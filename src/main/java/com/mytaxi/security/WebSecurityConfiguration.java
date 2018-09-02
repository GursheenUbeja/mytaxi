package com.mytaxi.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {

			// -- swagger ui
			//"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/** , /*" };
			"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/** ,/login" };

	/*@Override
	public void configure( HttpSecurity http) throws Exception {
		
		//All post man
	
	http.authorizeRequests().antMatchers(AUTH_WHITELIST).authenticated()
	.and().authorizeRequests().antMatchers("/**").permitAll();
	
	.antMatchers(HttpMethod.POST, "/login").authenticated().and()
		.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
	.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//
//		http.httpBasic().and().authorizeRequests().antMatchers(AUTH_WHITELIST).fullyAuthenticated()
//		.and().authorizeRequests()
//		.antMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated().and()
//		.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
//				UsernamePasswordAuthenticationFilter.class)
//	.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//

		
		
		http.httpBasic().and().authorizeRequests().antMatchers(AUTH_WHITELIST).authenticated()
		.and().authorizeRequests().antMatchers("/**").permitAll();
		
		
	}
*/
	@Override
	public void configure(WebSecurity web) {

		 // Securing every api with different parametres and asking for one time login
	
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
		
		web.ignoring().antMatchers(
	            HttpMethod.POST,
	            "/v1/**"
	    );
		
		web.ignoring().antMatchers(
	            HttpMethod.GET,
	            "/v1/**"
	    );
		

		web.ignoring().antMatchers(
	            HttpMethod.PUT,
	            "/v1/**"
	    );
		
			
		web.ignoring().antMatchers(
	            HttpMethod.DELETE,
	            "/v1/**"
	    );
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Create a default account
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("test")
				.password("test123").roles("USER").and().withUser("test1").password("test123").roles("ADMIN");
	}

}
