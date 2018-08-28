package com.mytaxi.filterSearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.service.search.DefaultSearchService;

public class SearchUserName implements Search {

	@Autowired
	DefaultSearchService searchUtility;

	List<DriverDO> driverList;
	List<CarDO> carList;
	Map<Long, DriverDO> driverMap = new HashMap<>();


	public SearchUserName(final DefaultSearchService search) {
		this.searchUtility = search;

	}

	@Override
	public List<DriverDO> meets(SearchDTO search) {

		driverList = searchUtility.getDriversList();

		return driverList.stream().filter(driver -> driver.getUsername().matches(search.getUsername()))
				.collect(Collectors.toList());

	}

}
