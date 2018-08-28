package com.mytaxi.service.selectCar;

import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.EntityNotFoundException;

public interface SelectCarService {

	void selectCar(Long carId, Long driverId) throws EntityNotFoundException ,CarAlreadyInUseException;

	void updateSelected(Long carId, Boolean selected);
	
	void deselectCar(Long carId, Long driverId) throws EntityNotFoundException, CarAlreadyInUseException;
	

}
