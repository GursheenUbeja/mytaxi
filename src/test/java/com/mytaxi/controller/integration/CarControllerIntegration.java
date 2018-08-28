package com.mytaxi.controller.integration;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerIntegration {

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void testGetCar() {

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/cars");
		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
		int statusCodeValue = response.getStatusCodeValue();

		Assert.assertTrue(statusCodeValue == 200);

	}

	@Test
	public void testGetAllDrivers() {

		TestRestTemplate restTemplate = new TestRestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/cars");

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl).queryParam("onlineStatus",
				OnlineStatus.ONLINE);
		System.out.println(builder.buildAndExpand().toUri());
		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		HttpEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, entity,
				String.class);
		int statusCodeValue = response.getBody().length();

		Assert.assertTrue(statusCodeValue > 0);

	}

	@Test
	public void deleteCar() {

		TestRestTemplate restTemplate = new TestRestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/cars") + "/1";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl);
		System.out.println(builder.buildAndExpand().toUri());
		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.DELETE,
				entity, String.class);
		int statusCodeValue = response.getStatusCodeValue();

		Assert.assertTrue(statusCodeValue == 200);

	}

	@Test
	public void updateCar() throws JSONException {

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");

		String fooResourceUrl = createURLWithPort("/v1/cars") + "/1";

		CarDTO dto = new CarDTO(1L, "ABC", "HYUNDAI", SeatCount.Six, Rating.Two, EngineType.Petrol);

		JSONObject json = new JSONObject();
		json.put("carDTO", dto);
		HttpEntity<CarDTO> httpEntity = new HttpEntity<CarDTO>(dto);

		ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.PUT, httpEntity,
				String.class);

		Assert.assertTrue(response.getStatusCodeValue() == 200);

	}

	@Test
	public void createCar() throws JSONException {

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");

		String fooResourceUrl = createURLWithPort("/v1/cars");

		CarDTO dto = new CarDTO(0L, "xre", "HYUNDAI", SeatCount.Six, Rating.Two, EngineType.Petrol);

		JSONObject json = new JSONObject();
		json.put("carDTO", dto);
		HttpEntity<CarDTO> httpEntity = new HttpEntity<CarDTO>(dto);

		ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, httpEntity,
				String.class);

		Assert.assertTrue(response.getStatusCodeValue() == 201);

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
