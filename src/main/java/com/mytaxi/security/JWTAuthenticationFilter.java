package com.mytaxi.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {

	
	
	boolean swag = true;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (request instanceof ServletRequest) {

			HttpServletRequest http = (HttpServletRequest) request;

			if (swag) {
				Principal userPrincipal = http.getUserPrincipal();
				System.out.println(userPrincipal.getName());
				TokenAuthenticationService.addAuthentication((HttpServletResponse) response,
						userPrincipal.getName().toString());
				swag = false;

			}

		}

		if (true) {

			Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}

	}

}
