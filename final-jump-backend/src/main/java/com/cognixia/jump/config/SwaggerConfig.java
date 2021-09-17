package com.cognixia.jump.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


// documentation html page: http://localhost:8080/swagger-ui.html 
// API with all the swagger documentation: http://localhost:8080/v2/api-docs 

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		// here is the page to create with all of the documentation for the apis in this project
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis( RequestHandlerSelectors.withClassAnnotation(RestController.class) )
				.paths( PathSelectors.any() )
				.build()
				.apiInfo( apiDetails() );
		
	}
	
	public ApiInfo apiDetails() {
		
		return new ApiInfo("Car API", // title of the documentation
						  "Open source API for obtaining/updating car information.", // description
						  "1.0", // version
						  "Free to use", // terms of use
						  new Contact("Orquidia", "http://github.com/notuntil4", "orquidia.moreno@cognixia.com"), // contact info
						  "API License",  // license
						  "http://github.com/notuntil4", // url to the license 
						  Collections.emptyList()); // list of vendors
	}

}






