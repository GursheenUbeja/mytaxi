package com.mytaxi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class CarRepositoryTest {

	@Autowired
	CarRepository repository;

	@Test
	public void testGetCar() {

		Long carId = 1L;
		Assert.assertNotNull(repository.getOne(carId));
	}

	@Test
	public void testFindByDeleted() {

		Long carId = 1L;
		Assert.assertTrue((repository.findByDeleted(false).size() > 1));
	}

	@Transactional
	@Test
	public void testUpdateDriverInCar() {

		Long carId = 1L;
		Long driverId = 1L;
		repository.updateDriverInCar(carId, driverId);

		CarDO car = repository.getOne(1L);

		Assert.assertTrue(car.getDriverid().equals(driverId));

	}

}
