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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.DefaultCarService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CarService {

	private MockMvc mvc;

	private static final ObjectMapper mapper = new ObjectMapper();

	@InjectMocks
	private DefaultCarService defCarService;

	@Autowired
	WebApplicationContext context;

	@Mock
	CarRepository repository;

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
	CarDO cacr;

	@Test
	public void testFindCarById() throws EntityNotFoundException {
		Long carId = 1L;
		CarDO find = defCarService.find(carId);
		CarDO find2 = repository.getOne(carId);
		assertEquals(find, find2);

	}

	@Test
	public void testAllCars() throws EntityNotFoundException {

		List<CarDO> findByDeleted = defCarService.findByDeleted(false);
		List<CarDO> findByDeleted2 = repository.findByDeleted(false);
		assertEquals(findByDeleted, findByDeleted2);
	}

	@Test
	public void createCar() throws ConstraintsViolationException {

		CarDTO dto = new CarDTO(100L, "XYZ", "HYUNDAI", SeatCount.Six, Rating.Two, EngineType.Petrol);
				defCarService.create(CarMapper.makeCarDO(dto));
		List<CarDO> findByLicenseplate = repository.findByLicenseplate("XYZ");
		assertNotNull(findByLicenseplate);

	}
	
	
	@Test
	public void deleteCar() throws  EntityNotFoundException {

		defCarService.delete(1L);
		
		CarDO findByLicenseplate = repository.getOne(1L);
		assertEquals(findByLicenseplate.getDeleted(), true);

	}

}
