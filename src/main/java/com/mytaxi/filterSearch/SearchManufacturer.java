package com.mytaxi.filterSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.service.search.DefaultSearchService;

public class SearchManufacturer implements Search {

	@Autowired
	DefaultSearchService searchUtility;

	List<DriverDO> driverList;
	List<CarDO> carList;

	public SearchManufacturer(final DefaultSearchService searchUtility) {
		this.searchUtility = searchUtility;

	}

	@Override
	public List<DriverDO> meets(SearchDTO search) {

		List<DriverDO> filteredDrivers = new ArrayList<>();
		driverList = searchUtility.getDriversList();
		carList = searchUtility.getCarListByManufacturer(search.getManufacturer());
		filteredDrivers = searchUtility.getFilteredList(driverList, carList);
		return filteredDrivers;
	}

}
