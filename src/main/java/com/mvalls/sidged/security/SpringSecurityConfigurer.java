package com.mvalls.sidged.security;

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

import com.mvalls.sidged.security.jwt.JwtAuthenticationFilter;
import com.mvalls.sidged.security.jwt.JwtAuthenticationProvider;
import com.mvalls.sidged.security.jwt.JwtAuthenticationSuccessHandler;

/**
 * 
 * @author mcvalls
 * 
 * Configuration class where I declare all the necessaries beans for the
 * Spring Configuration setup.
 * Also the configure() method is override to control the requests.
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
