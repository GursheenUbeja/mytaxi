package com.mytaxi.service.selectCar;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DefaultDriverService;

@Service
public class DefaultSelectCarService implements SelectCarService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final CarRepository carRepository;
	private final DriverRepository driverRepository;

	public DefaultSelectCarService(final CarRepository carRepository, final DriverRepository driverRepository) {
		this.carRepository = carRepository;
		this.driverRepository = driverRepository;
	}

	
	

	/**
	 * Selects a car by id and updates Car table with driver id and selected = true.
	 *
	 * @param carId , driverId
	 * @return found car
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found or if no car id with given i was found
	 * @throws CarAlreadyInUseException
	 * 			   if the selected car is already set to selected true            
	 */
	
	
	
	@Override
	public void selectCar(Long carId, Long driverId) throws EntityNotFoundException, CarAlreadyInUseException {

		DriverDO driver = driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));

		CarDO car = carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));

		if (driver.getDeleted().equals(false) && driver.getOnlineStatus().equals(OnlineStatus.ONLINE)
				&& car.getDeleted().equals(false) && car.getSelected().equals(false)) {
			carRepository.updateDriverInCar(carId, driverId);
			updateSelected(car.getId(), true);
		} else {
			throw new CarAlreadyInUseException("Select failed  : Car or Driver Id not valid , Or Driver is in Offline state or car is in already in use");
		}

	}
	
	
	
	/**
	 * Deselects a car by id and updates Car table with driver id = 0 and selected = false.
	 *
	 * @param carId , driverId
	 * @return found car
	 * @throws EntityNotFoundException
	 *             if no driver with the given id was found or if no car id with given i was found
	 * @throws CarAlreadyInUseException
	 * 			   if the selected car is already set to selected true            
	 */
	
	

	@Override
	public void updateSelected(Long carId, Boolean selected) {
		carRepository.updateSelected(carId, selected);
	}

	@Override
	public void deselectCar(Long carId, Long driverId) throws EntityNotFoundException, CarAlreadyInUseException {

		DriverDO driver = driverRepository.findById(driverId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));

		CarDO car = carRepository.findById(carId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));

		if (driver.getDeleted().equals(false) && driver.getOnlineStatus().equals(OnlineStatus.ONLINE)
				&& car.getDeleted().equals(false) && car.getSelected().equals(true)
				&& car.getDriverid().equals(driver.getId())) {
			carRepository.updateDriverInCar(carId, driverId);
			updateSelected(car.getId(), true);
		} else {
			throw new CarAlreadyInUseException("Car is not in selected state");
		}

	}

}
