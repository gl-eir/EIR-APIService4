package com.ceir.CeirCode.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesReader {

	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String dialect;
	
	@Value("${local-ip}")
	public String localIp;
}
