package com.mytaxi.controller.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.MytaxiServerApplicantTestApplication;
import com.mytaxi.controller.CarController;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;
import com.mytaxi.service.car.CarService;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MytaxiServerApplicantTestApplication.class)
@ActiveProfiles("test")

public class CarControllerTest {
	
	private MockMvc mvc;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Mock
	private CarService carService;

	@InjectMocks
	private CarController carController;

	@Autowired
	WebApplicationContext context;

	@BeforeClass
	public static void setUp() {
		MockitoAnnotations.initMocks(CarController.class);
	}

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	@Test
	public void testGetCar() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/cars/{carId}", 1);

		ResultActions perform = mvc.perform(requestBuilder);
		ResultActions andExpect = perform.andExpect(MockMvcResultMatchers.status().isOk());
		MvcResult result = andExpect.andReturn();

		final String responseBody = result.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));
	}

	
	@Test
	public void testAllCars() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/cars")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		final String responseBody = result.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));
	}


	@Test
	public void testCreateDriver() throws Exception {
		CarDTO dto = new CarDTO(1L, "XYZ", "HYUNDAI", SeatCount.Six, Rating.Two, EngineType.Petrol);

		String jsonInString = mapper.writeValueAsString(dto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/cars")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

		MvcResult result = mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

		final String responseBody = result.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));

	}
	
	
	
	@Test
	public void testUpdateCar() throws Exception {
		
		CarDTO dto = new CarDTO(0L, "ABC", "HYUNDAI", SeatCount.Six, Rating.Two, EngineType.Petrol);
		String jsonInString = mapper.writeValueAsString(dto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/cars/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

		

		 ResultActions perform = mvc.perform(requestBuilder);
		
		 ResultActions andExpect = perform.andExpect(MockMvcResultMatchers.status().isOk());
		
		 MvcResult andReturn = andExpect.andReturn();	
		
		final int responseBody = andReturn.getResponse().getStatus();
		Assert.assertTrue(responseBody==200);
		
	}


}
