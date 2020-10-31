package com.mvalls.sidged.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.mvalls.sidged.rest.security.jwt.JwtAuthenticationFilter;
import com.mvalls.sidged.rest.security.jwt.JwtAuthenticationProvider;
import com.mvalls.sidged.rest.security.jwt.JwtAuthenticationSuccessHandler;

/**
 * 
 * @author mcvalls
 * 
 * Configuration class where I declare all the necessaries beans for the
 * Spring Configuration setup.
 * Also the configure() method is override to control the requests.
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 */
@Configuration
@ComponentScan(basePackages = "com.tsq.login")
@EnableWebSecurity
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
	@Autowired private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable();
	}
	
	/**
	 * @return the JwtAuthenticationFilter
	 */
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		return filter;
	}

	/**
	 * Load the JwtAuthenticationProvider into the providers list, which will
	 * process the incoming token.
	 * 
	 * @return a ProviderManager with the JwtAuthenticationProvider loaded.
	 */
	@Bean("authenticationManager")
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(jwtAuthenticationProvider);
		return new ProviderManager(providers);
	}
	
}
