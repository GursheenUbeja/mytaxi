package com.mytaxi.controller;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.datatransferobject.SearchDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DriverService;
import com.mytaxi.service.search.SearchService;
import com.mytaxi.service.selectCar.SelectCarService;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;
    private final SelectCarService selectCar;
    private final SearchService searchService;

    @Autowired
    public DriverController(final DriverService driverService , final SelectCarService selectCar , final SearchService searchService)
    {
        this.driverService = driverService;
        this.selectCar = selectCar;
        this.searchService = searchService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @GetMapping(value = "/selectCar")
    public void selectCar(@Valid @RequestParam Long driverId , @RequestParam Long carId) throws EntityNotFoundException, CarAlreadyInUseException {
    	selectCar.selectCar(carId, driverId);
    }
    
    

    @GetMapping(value = "/deselectCar")
    public void deselectCar(@Valid @RequestParam Long driverId , @RequestParam Long carId) throws EntityNotFoundException, CarAlreadyInUseException {
    	selectCar.deselectCar(carId, driverId);
    }
    
    
    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }

    
    @PostMapping(value = "/search")
    public Set<DriverDO> searchDriver(@Valid @RequestBody SearchDTO searchDTO) throws ConstraintsViolationException{
    	
    	
    	return searchService.search(searchDTO);
    	
    	
    }

    @PutMapping("/{driverId}")
    public void updateLocation(
        @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }
}
