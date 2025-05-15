//package com.ceir.CeirCode.configuration;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//
//import com.ceir.CeirCode.service.UserDetailsServiceImpl;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//@EnableSwagger2
//@Configuration
//public class AppConfiguration {
//	
//	@Autowired
//	UserDetailsServiceImpl userServiceImpl;
//
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//		        .apiInfo(apiEndPointsInfo())
//		        .securityContexts(Arrays.asList(securityContext()))
//		        .securitySchemes(Arrays.asList(apiKey()))
//		        .select()
//		        .apis(RequestHandlerSelectors.basePackage("com.ceir.CeirCode.controller"))
//		        .paths(PathSelectors.any())
//		        .build();
//	}
//
//	private ApiInfo apiEndPointsInfo() {
//		return new ApiInfoBuilder().title("CEIR Configuration APIs Document")
//				.description("Configuration Management REST APIs").license("Apache 2.0")
//				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0").build();
//	}
//	
//	private ApiKey apiKey() {
//	    return new ApiKey("JWT", "Authorization", "header");
//	}
//	
//	private SecurityContext securityContext() {
//	    return SecurityContext.builder()
//	        .securityReferences(defaultAuth())
//	        .build();
//	  }
//
//	  List<SecurityReference> defaultAuth() {
//	    AuthorizationScope authorizationScope
//	        = new AuthorizationScope("global", "accessEverything");
//	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//	    authorizationScopes[0] = authorizationScope;
//	    return (List<SecurityReference>) Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//	  }
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	    authProvider.setUserDetailsService(userServiceImpl.userDetailsService());
//	    authProvider.setPasswordEncoder(passwordEncoder());
//	    return authProvider;
//	}
//	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//	
//	@Bean
//	public NoOpPasswordEncoder passwordEncoder() {
//	  return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//	}
//
//}


package com.ceir.CeirCode.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@Configuration
public class AppConfiguration {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.ceir.CeirCode.controller"))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiEndPointsInfo());
	}

	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title("CEIR Configuration APIs Document")
				.description("Configuration Management REST APIs").license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version("1.0.0").build();
	}

}