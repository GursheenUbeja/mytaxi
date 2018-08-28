package com.mytaxi.service.search;

import java.util.Set;

import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.DriverDO;

public interface SearchService {
	
	
		public Set<DriverDO> search(SearchDTO searchDTO);

}
	