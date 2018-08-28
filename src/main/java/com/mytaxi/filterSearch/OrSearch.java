package com.mytaxi.filterSearch;

import java.util.ArrayList;
import java.util.List;

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.DriverDO;

public class OrSearch implements Search {

	private Search[] searches;

	List<DriverDO> filteredDrivers = new ArrayList<>();

	public OrSearch(Search... searches) {
		this.searches = searches;
	}

	@Override
	public List<DriverDO> meets(SearchDTO searchDTO) {

		for (Search search : searches) {
			if (search.meets(searchDTO).size() > 0) {
				filteredDrivers.addAll(search.meets(searchDTO));
			}

		}

		return filteredDrivers;
	}
	
	
	
}
