package com.lsoftware.pastebinbackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lsoftware.pastebinbackend.models.responses.UserRest;
import com.lsoftware.pastebinbackend.security.AppProperties;
import com.lsoftware.pastebinbackend.shared.dto.UserDTO;


@SpringBootApplication
@EnableJpaAuditing
public class PastebinBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PastebinBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	@Bean(name = "AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}
	
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.typeMap(UserDTO.class, UserRest.class).addMappings(m -> m.skip(UserRest::setPosts));

		return mapper;
	}
	
	
}
