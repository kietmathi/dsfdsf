package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.filter.TokenFilter;

@Configuration
public class TokenConfig {
	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}
}
