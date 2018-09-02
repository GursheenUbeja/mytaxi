package com.mytaxi.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import static java.util.Collections.emptyList;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static String token = null;
	
	
	public static String getToken() {
		return token;
	}


	public static void setToken(String token) {
		TokenAuthenticationService.token = token;
	}


	static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder()
		        .setSubject(username)
		        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
		        .signWith(SignatureAlgorithm.HS512, SECRET)
		        .compact();
		    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		    setToken(JWT);
		    System.out.println("token : " + JWT);
		  }


	 static Authentication getAuthentication(HttpServletRequest request) {
		    String token = request.getHeader(HEADER_STRING);
		    
		    System.out.println("" + token);
		    System.out.println("" + getToken().replace(TOKEN_PREFIX, ""));
		    if (token != null) {
		      // parse the token.
		    	
		    	if(token.startsWith(TOKEN_PREFIX)) {
		    		String user = Jwts.parser()
		  		          .setSigningKey(SECRET)
		  		          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
		  		          .getBody()
		  		          .getSubject();

		  		      if(token.equals(getToken())) {
		  		    	  return user != null ?
		  				          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
		  				          null;
		  		      }
		    	}
		      
		      
		    }
		    return null;
		  }
}
