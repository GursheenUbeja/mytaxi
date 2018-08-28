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

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class DriverControllerIntegration {

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void testGetDrivers() {

		RestTemplate restTemplate = new RestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/drivers");
		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
		int statusCodeValue = response.getStatusCodeValue();

		Assert.assertTrue(statusCodeValue == 200);

	}

	@Test
	public void testGetAllDrivers() {

		TestRestTemplate restTemplate = new TestRestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/drivers");

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl);
		System.out.println(builder.buildAndExpand().toUri());
		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		HttpEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, entity,
				String.class);
		int statusCodeValue = response.getBody().length();

		Assert.assertTrue(statusCodeValue > 0);

	}

	@Test
	public void testUpdate() {

		TestRestTemplate restTemplate = new TestRestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/drivers") + "/1";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl).queryParam("longitude", 12.34)
				.queryParam("latitude", 56.78);

		System.out.println(builder.buildAndExpand().toUri());

		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.PUT,
				entity, String.class);

		Assert.assertTrue(response.getStatusCode().value() == 200);

	}

	@Test
	public void testSearch() throws JSONException {

		TestRestTemplate restTemplate = new TestRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");

		SearchDTO search = new SearchDTO("abc", "xyz", "Toyota", Rating.Five, EngineType.Gas,
				OnlineStatus.ONLINE);
		JSONObject json = new JSONObject();
		json.put("searchDTO", search);

		String fooResourceUrl = createURLWithPort("/v1/drivers/search");

		HttpEntity<SearchDTO> httpEntity = new HttpEntity<SearchDTO>(search);
		
		ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, httpEntity,
				String.class);

		
		Assert.assertTrue(response.getStatusCodeValue()==200);

	}

	@Test
	public void selectCar() throws JSONException {

		TestRestTemplate restTemplate = new TestRestTemplate();

		String fooResourceUrl = createURLWithPort("/v1/drivers/selectCar");

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl).queryParam("driverId", 4)
				.queryParam("carId", 1);

		System.out.println(builder.buildAndExpand().toUri());

		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET,
				entity, String.class);

		Assert.assertTrue(response.getStatusCode().value() == 200);

	}

	@Test
	public void deselectCar() throws JSONException {

		TestRestTemplate restTemplate = new TestRestTemplate();

		String fooResourceUrl = createURLWithPort("/v1/drivers/deselectCar");

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl).queryParam("driverId", 4)
				.queryParam("carId", 1);

		System.out.println(builder.buildAndExpand().toUri());

		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET,
				entity, String.class);

		Assert.assertTrue(response.getStatusCode().value() == 200);

	}

	@Test
	public void deleteDriver() {

		TestRestTemplate restTemplate = new TestRestTemplate();
		String fooResourceUrl = createURLWithPort("/v1/drivers") + "/1";

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fooResourceUrl);
		System.out.println(builder.buildAndExpand().toUri());
		HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.DELETE,
				entity, String.class);
		int statusCodeValue = response.getStatusCodeValue();

		Assert.assertTrue(statusCodeValue == 200);

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
