package com.pamesh.rest.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pamesh.rest.vo.User;

/**
 * 
 * @author Pamesh Bansal
 * Class to convert string defined in properties file to java object
 * see {@link ApplicationProperties#getUser()}
 */
@Component
@ConfigurationPropertiesBinding
public class UserConverter implements Converter<String,User> {

	@Override
	public User convert(String source) {
		String [] value = source.split(",");
		return new User(value[0],value[1]);
	}

}
