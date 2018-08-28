package com.mytaxi.service.car;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DefaultDriverService;

@Service
public class DefaultCarService implements CarService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final CarRepository carRepository;

	public DefaultCarService(final CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	/**
	 * Selects a car by id.
	 *
	 * @param carId
	 * @return found car
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found.
	 */

	@Override
	public CarDO find(Long carId) throws EntityNotFoundException {
		return findCarChecked(carId);
	}

	/**
	 * Creates a new car.
	 *
	 * @param carDO
	 * @return
	 * @throws ConstraintsViolationException
	 *             if a car already exists.
	 */
	@Override
	public CarDO create(CarDO carDO) throws ConstraintsViolationException {

		CarDO car;
		try {
			car = carRepository.save(carDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return car;
	}

	/**
	 * Deletes an existing car by id.
	 *
	 * @param carId
	 * @throws EntityNotFoundException
	 *             if no car with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long carId) throws EntityNotFoundException {
		CarDO carDO = findCarChecked(carId);
		if (carDO != null) {
			carDO.setDeleted(true);
			carRepository.save(carDO);
		}

	}

	/**
	 * Updates an existing car by id.
	 *
	 * @param carId
	 *            , CarRequest Body
	 * @throws EntityNotFoundException
	 *             if no car with the given id was found.
	 */

	@Override
	@Transactional
	public void updateCar(long carId, CarDO carDORequest) throws EntityNotFoundException {
		CarDO carDO = carRepository.getOne(carId);
		if (carDO != null) {

			carDO.setManufacturer(carDORequest.getManufacturer());
			carDO.setSeatcount(carDORequest.getSeatcount());
			carDO.setRating(carDORequest.getRating());
			carDO.setLicenseplate(carDORequest.getLicenseplate());

			carRepository.save(carDO);

		} else {
			throw new EntityNotFoundException(carId + "Not found");
		}

	}

	private CarDO findCarChecked(Long carId) throws EntityNotFoundException {

		CarDO car = carRepository.getOne(carId);

		System.out.println(car);
		return carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
	}

	@Override
	public List<CarDO> findByDeleted(Boolean deleted) {
		return carRepository.findByDeleted(false);
	}

	@Override
	public List<CarDO> findBySelected(Boolean selected) {
		return carRepository.findBySelected(false);
	}

}
