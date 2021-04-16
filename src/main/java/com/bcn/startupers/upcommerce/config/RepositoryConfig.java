package com.bcn.startupers.upcommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.bcn.startupers.upcommerce.model.Role;
import com.bcn.startupers.upcommerce.model.User;


/**
 * This class enables objects id's
 * @author yhuzo
 *
 */
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {	
	config.exposeIdsFor(User.class, Role.class);
	}

}
