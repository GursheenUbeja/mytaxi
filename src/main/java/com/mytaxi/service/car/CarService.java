package com.mytaxi.service.car;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

public interface CarService {

	CarDO find(Long carId) throws EntityNotFoundException;

	CarDO create(CarDO carDO) throws ConstraintsViolationException;

	void delete(Long carId) throws EntityNotFoundException;

	List<CarDO> findBySelected(Boolean selected);

	List<CarDO> findByDeleted(Boolean deleted);

	void updateCar(long carId, CarDO carDORequest) throws EntityNotFoundException;

}
