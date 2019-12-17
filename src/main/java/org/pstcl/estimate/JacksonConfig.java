package org.pstcl.estimate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {


	@Autowired
	private ObjectMapper objectMapper;


	@PostConstruct
	public void setUp() {
		
		objectMapper.registerModule(new JavaTimeModule());

	}

}
