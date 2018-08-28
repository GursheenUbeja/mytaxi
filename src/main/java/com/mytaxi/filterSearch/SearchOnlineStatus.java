package com.mytaxi.filterSearch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.service.search.DefaultSearchService;

public class SearchOnlineStatus implements Search{
	
	@Autowired
	DefaultSearchService searchUtility;

	List<DriverDO> driverList;
	List<CarDO> carList;

	public SearchOnlineStatus(final DefaultSearchService searchUtility) {
		this.searchUtility = searchUtility;

	}


	@Override
	public List<DriverDO> meets(SearchDTO search) {
		driverList = searchUtility.getDriversList();

		return driverList.stream().filter(driver -> driver.getOnlineStatus().toString().matches(search.getOnlineStatus().toString()))
				.collect(Collectors.toList());

	}

}
