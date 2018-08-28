package com.mytaxi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.DefaultCarService;
import com.mytaxi.service.driver.DefaultDriverService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DriverService {
	
	
	private MockMvc mvc;

	private static final ObjectMapper mapper = new ObjectMapper();

	@InjectMocks
	private DefaultDriverService defDriverService;

	@Autowired
	WebApplicationContext context;

	@Mock
	DriverRepository repository;

	@BeforeClass
	public static void setUp() {

		MockitoAnnotations.initMocks(DefaultCarService.class);
		// mvc = MockMvcBuilders.standaloneSetup(DefaultCarService.class).build();
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	DriverDO driver;
	
	
	@Test
	public void testFindDriverById() throws EntityNotFoundException {
		Long driverId = 1L;
		DriverDO find = defDriverService.find(driverId);
		DriverDO find2 = repository.getOne(driverId);
		assertEquals(find, find2);

	}

	@Test
	public void testAllDrivers() throws EntityNotFoundException {

		List<DriverDO> find1 = defDriverService.find(OnlineStatus.ONLINE);
		List<DriverDO> find2 = repository.findByDeleted(false);
		assertEquals(find1, find2);
	}


	
/*	@Test
	public void createDriver() throws ConstraintsViolationException {

		DriverDTO dto = new DriverDTO(1L, "XYZ", "HYUNDAI", null);
		repository.create(DriverMapper.makeDriverDO(dto));
		List<DriverDO> findByLicenseplate = repository.findByUsername("XYZ");
		assertNotNull(findByLicenseplate);

	}*/
	
	@Test
	public void deleteCar() throws  EntityNotFoundException {

		defDriverService.delete(1L);
		
		DriverDO findByLicenseplate = repository.getOne(1L);
		assertEquals(findByLicenseplate.getDeleted(), true);

	}


}
