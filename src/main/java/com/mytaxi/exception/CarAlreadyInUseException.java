package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car or Driver Id not valid , Or Driver is in Offline state or car is in already in use")
public class CarAlreadyInUseException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public CarAlreadyInUseException(String message)
    {
       
    }

}

