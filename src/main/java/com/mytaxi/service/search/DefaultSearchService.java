package com.mytaxi.service.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.filterSearch.OrSearch;
import com.mytaxi.filterSearch.SearchEngineType;
import com.mytaxi.filterSearch.SearchLicensePlate;
import com.mytaxi.filterSearch.SearchManufacturer;
import com.mytaxi.filterSearch.SearchOnlineStatus;
import com.mytaxi.filterSearch.SearchRating;
import com.mytaxi.filterSearch.SearchSeatCount;
import com.mytaxi.filterSearch.SearchUserName;
import com.mytaxi.service.driver.DefaultDriverService;

@Service
public class DefaultSearchService implements SearchService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

	private final CarRepository carRepository;
	private final DriverRepository driverRepository;

	public DefaultSearchService(final CarRepository carRepository, final DriverRepository driverRepository) {
		this.carRepository = carRepository;
		this.driverRepository = driverRepository;
	}

	/**
	 * Selects a driver by id.
	 *
	 * @param SearchDTO
	 *            as json object , SearchDTO consists of a combination of driver as
	 *            well as car attributes
	 * @return List of drivers
	 * 
	 */

	@Override
	public Set<DriverDO> search(SearchDTO searchDTO) {

		SearchDTO.SearchDTOBuilder searchDTOBuilder = SearchDTO.newBuilder().setEnginetype(searchDTO.getEnginetype())
				.setLicenseplate(searchDTO.getLicenseplate()).setManufacturer(searchDTO.getManufacturer())
				.setOnlineStatus(searchDTO.getOnlineStatus()).setRating(searchDTO.getRating())
				.setUsername(searchDTO.getUsername());

		OrSearch orSearch = new OrSearch(new SearchUserName(this), new SearchEngineType(this),
				new SearchLicensePlate(this), new SearchManufacturer(this), new SearchOnlineStatus(this),
				new SearchRating(this));

		List<DriverDO> driversList = orSearch.meets(searchDTOBuilder.createSearchDTO());

		return driversList.stream().collect(Collectors.toSet());
	}

	public List<DriverDO> getDriversList() {

		return driverRepository.findByDeleted(false);
	}

	public List<CarDO> getCarList() {

		return carRepository.findByDeleted(false);
	}

	public List<CarDO> getCarListByManufacturer(String manufacturer) {

		return carRepository.findByManufacturer(manufacturer);

	}

	public List<CarDO> getCarListByRating(Rating rating) {

		return carRepository.findByRating(rating);

	}

	public List<CarDO> getCarByEngineType(EngineType engine) {

		return carRepository.findByEnginetype(engine);

	}

	public List<CarDO> getCarByLicensePlate(String license) {

		return carRepository.findByLicenseplate(license);

	}

	public List<DriverDO> getFilteredList(List<DriverDO> driverList, List<CarDO> carList) {
		Map<Long, DriverDO> driverMap = new HashMap<>();
		List<DriverDO> filteredDrivers = new ArrayList<>();
		driverMap = driverList.stream().collect(Collectors.toMap(DriverDO::getId, Function.identity()));
		for (CarDO car : carList) {
			if (driverMap.containsKey(car.getDriverid())) {
				filteredDrivers.add(driverMap.get(car.getDriverid()));
			}
		}
		return filteredDrivers;
	}

}
