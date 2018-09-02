package com.mytaxi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.security.TokenAuthenticationService;

@RestController
@RequestMapping("v1/tokens")
public class TokenController {
	
	@GetMapping
	public String getToken()
    {
		String str = TokenAuthenticationService.getToken();
		str = str.replaceAll("(\\r|\\n)", str);
        return "Bearer " + str;
    }
	

}
