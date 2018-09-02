package com.mytaxi;

import com.mytaxi.security.JWTAuthenticationFilter;
import com.mytaxi.util.LoggingInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MytaxiServerApplicantTestApplication extends WebMvcConfigurerAdapter
{

	
	@Autowired
	JWTAuthenticationFilter jwt;
	@Autowired
	private ApplicationContext context;	
	
    public static void main(String[] args)
    {
        SpringApplication.run(MytaxiServerApplicantTestApplication.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }


    @Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(generateApiInfo());
    }

    

  /*  @Bean
    public FilterRegistrationBean registration(JWTAuthenticationFilter jwt) {
    
    	
    	
    	
        FilterRegistrationBean registration = new FilterRegistrationBean(jwt);
        registration.addUrlPatterns("/*");
       // registration.setEnabled(false);
        registration.setName("Gursheen");
        return registration;
    }*/
    

    
    
    private ApiInfo generateApiInfo()
    {
        return new ApiInfo("mytaxi Server Applicant Test Service", "This service is to check the technology knowledge of a server applicant for mytaxi.", "Version 1.0 - mw",
            "urn:tos", "career@mytaxi.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
    }
}
