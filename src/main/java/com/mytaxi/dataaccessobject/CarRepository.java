package com.mytaxi.dataaccessobject;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.Rating;
import com.mytaxi.domainvalue.SeatCount;

@Repository
public interface CarRepository extends CrudRepository<CarDO, Long>{
	
	
	public CarDO getOne(Long carId);
	
	public List<CarDO>  findBySelected(Boolean selected);
	public List<CarDO> findByDeleted(Boolean deleted);
	
	
	public List<CarDO> findByManufacturer(String manufacturer);
	
	public List<CarDO> findByRating(Rating rating);
	
	public List<CarDO> findByEnginetype(EngineType engine);
	
	
	public List<CarDO> findByLicenseplate(String license);

	@Transactional
	@Modifying
	@Query("update CarDO c set c.driverid = ?2 where c.id  = ?1")
	void updateDriverInCar(Long carId, Long driverId);

	@Transactional
	@Modifying
	@Query("update CarDO c set c.selected = ?2 where c.id  = ?1")
	void updateSelected(Long carId, Boolean selected);


}
