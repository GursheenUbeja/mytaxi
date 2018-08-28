package com.mytaxi.filterSearch;

import java.util.List;

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.DriverDO;

public interface Search {
	
	public  List<DriverDO> meets(SearchDTO search);

}
