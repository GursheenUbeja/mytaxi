package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;

public class CarMapper {

	public static CarDO makeCarDO(CarDTO carDTO) {

		CarDO.CarDOBuilder carDOBuilder = CarDO.newBuilder().setEnginetype(carDTO.getEnginetype())
				.setLicenseplate(carDTO.getLicenseplate())
				.setManufacturer(carDTO.getManufacturer())
				.setRating(carDTO.getRating())
				.setSeatcount(carDTO.getSeatcount());
		
		return carDOBuilder.createCarDO();
		
	}

	public static CarDTO makeCarDTO(CarDO carDO) {
		CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder().setId(carDO.getId())
				.setLicenseplate(carDO.getLicenseplate())
				.setManufacturer(carDO.getManufacturer())
				.setRating(carDO.getRating())
				.setSeatcount(carDO.getSeatcount());
		

		return carDTOBuilder.createCarDTO();
	}
	
	  public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
	    {
	        return cars.stream()
	            .map(CarMapper::makeCarDTO)
	            .collect(Collectors.toList());
	    }

}
