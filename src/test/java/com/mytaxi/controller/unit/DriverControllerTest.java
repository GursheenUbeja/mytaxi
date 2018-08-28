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
import com.mytaxi.controller.DriverController;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;
import com.mytaxi.service.driver.DriverService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MytaxiServerApplicantTestApplication.class)
@ActiveProfiles("test")

public class DriverControllerTest {

	private MockMvc mvc;

	private static final ObjectMapper mapper = new ObjectMapper();

	@Mock
	private DriverService driverService;

	@InjectMocks
	private DriverController driverController;

	@Autowired
	WebApplicationContext context;

	@BeforeClass
	public static void setUp() {
		MockitoAnnotations.initMocks(DriverController.class);
	}

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testSearchDriver() throws Exception {

		SearchDTO search = new SearchDTO("abc", "xyz", "Toyota", Rating.Four, EngineType.Gas, OnlineStatus.ONLINE);

		String jsonInString = mapper.writeValueAsString(search);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/drivers/search")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

		ResultActions perform = mvc.perform(requestBuilder);

		ResultActions andExpect = perform.andExpect(MockMvcResultMatchers.status().isOk());

		MvcResult andReturn = andExpect.andReturn();

		final String responseBody = andReturn.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));

	}

	@Test
	public void testDriverSelectCar() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers/selectCar").param("driverId", "4")
				.param("carId", "1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		final int responseBody = result.getResponse().getStatus();
		Assert.assertTrue(responseBody == 200);
	}

	/*
	 * @Test public void testDriverDeSelectCar() throws Exception {
	 * 
	 * RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.get("/v1/drivers/deselectCar") .param("driverId","4")
	 * .param("carId", "1") .accept(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result =
	 * mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).
	 * andReturn();
	 * 
	 * 
	 * final int responseBody = result.getResponse().getStatus();
	 * Assert.assertTrue(responseBody==400); }
	 */

	@Test
	public void testUpdateDriver() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/drivers/1")

				.param("longitude", "12.34").param("latitude", "19.76").accept(MediaType.APPLICATION_JSON);

		ResultActions perform = mvc.perform(requestBuilder);

		ResultActions andExpect = perform.andExpect(MockMvcResultMatchers.status().isOk());

		MvcResult andReturn = andExpect.andReturn();

		final int responseBody = andReturn.getResponse().getStatus();
		Assert.assertTrue(responseBody == 200);

	}

	@Test
	public void testCreateDriver() throws Exception {
		DriverDTO driverDTO = new DriverDTO(0L, "abc", "abc", null);

		String jsonInString = mapper.writeValueAsString(driverDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/drivers")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonInString);

		MvcResult result = mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

		final String responseBody = result.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));

	}

	@Test
	public void testDriverOnlineStatus() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers")
				.param("onlineStatus", OnlineStatus.ONLINE.toString()).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		final String responseBody = result.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));
	}

	@Test
	public void testGetDriver() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/drivers/{driverId}", 1);

		ResultActions perform = mvc.perform(requestBuilder);
		ResultActions andExpect = perform.andExpect(MockMvcResultMatchers.status().isOk());
		MvcResult result = andExpect.andReturn();

		final String responseBody = result.getResponse().getContentAsString();
		Assert.assertTrue(responseBody.contains("id"));
	}

}
