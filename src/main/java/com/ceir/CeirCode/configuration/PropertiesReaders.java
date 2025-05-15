package com.ceir.CeirCode.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration

@PropertySources({
    @PropertySource(value = {"file:application.properties"}, ignoreResourceNotFound = true),
    @PropertySource(value = {"file:configuration.properties"}, ignoreResourceNotFound = true)
})

public class PropertiesReaders {

	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String dialect;
	
	@Value("${local-ip}")
	public String localIp;
        
        @Value("${serverName}")
	public String serverName;
   
            
        @Value("${module_name}")
        public String moduleName;

        @Value("${dbNamesff}")
      	 public   String dbName;
        
        @Value("${tableNameuse}")
   	 public   String TABLE_NAME;
    
        
}
