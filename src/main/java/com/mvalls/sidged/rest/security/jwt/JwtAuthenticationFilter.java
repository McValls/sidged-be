package com.mvalls.sidged.rest.security.jwt;

import static com.mvalls.sidged.rest.security.SecurityConstants.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * 
 * @author mcvalls
 * 
 * This class filter all the request to the server and mark as successful
 * only those where a valid JWT Token is present.
 *
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
 *
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private final List<String> validPaths = Arrays.asList(LOGIN_URL);
	
	protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}
	
	public JwtAuthenticationFilter() {
		super("/*");
	}
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return !isValidServletPath(request.getServletPath()) && !isPreflightOptions(request.getMethod());
	}
	
	private boolean isValidServletPath(String servletPath) {
		return validPaths.contains(servletPath);
	}
	
	private boolean isPreflightOptions(String method) {
		return method.equalsIgnoreCase(HttpMethod.OPTIONS.name());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			response.sendError(401, "No JWT token found in request headers");
			return null;
		}
		String authToken = header.substring(7);
		CustomJwtAuthenticationToken token = new CustomJwtAuthenticationToken(authToken);
		return getAuthenticationManager().authenticate(token);
	}
	
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        chain.doFilter(request, response);
    }

}
