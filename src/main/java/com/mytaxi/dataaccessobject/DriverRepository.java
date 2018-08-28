package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Database Access Object for driver table.
 * <p/>
 */
@Repository
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
    List<DriverDO> findByDeleted(Boolean deleted);
    DriverDO  getOne(Long driverId);
    DriverDO findByUsername(String username);
    
    
}
