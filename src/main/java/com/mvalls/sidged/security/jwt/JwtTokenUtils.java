package com.mvalls.sidged.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.rest.exceptions.UnauthorizedUserException;
import com.mvalls.sidged.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author mcvalls
 * Utils class to generate and parse tokens.
 */

@Component
public class JwtTokenUtils {

    private String secret = "Y0uR_S3cR3T!!!";

    /**
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    @SuppressWarnings("unchecked")
	public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            ArrayList<LinkedHashMap<String, String>> roles = (ArrayList<LinkedHashMap<String, String>>) body.get("roles");
            List<GrantedAuthority> authorityList = new ArrayList<>();
            for(LinkedHashMap<String, String> rol : roles) {
            	authorityList.add(new SimpleGrantedAuthority(rol.get("authority")));
            }

            User u = new CustomJwtUser(
            		(String) body.get("userId"), 
            		(String) body.get("password"), 
            		authorityList);
            return u;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }
    
    public String getUserName(String token, UserType userType) throws UnauthorizedUserException {
    	User user = this.parseToken(token);
    	boolean isValidUserType = user.getAuthorities().stream()
    		.anyMatch(authority -> authority.getAuthority().equalsIgnoreCase(userType.name()));
    	if(!isValidUserType) {
     		throw new UnauthorizedUserException();
    	}
    	
    	return user.getUsername();
    }

    public void setTokenToResponse(com.mvalls.sidged.login.User user, HttpServletResponse response) {
    	Collection<GrantedAuthority> authorities = new ArrayList<>();
    	authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));
    	CustomJwtUser customUser = new CustomJwtUser(user.getUsername(), user.getPassword(), authorities);
    	
    	String token = this.generateToken(customUser);
    	response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
    
    public String generateToken(User u) {
    		
        Claims claims = Jwts.claims()
        		.setSubject(u.getUsername())
        		.setExpiration(new Date(System.currentTimeMillis() + 172_800_000)); // 48hs
        claims.put("userId", u.getUsername());
        claims.put("password", u.getPassword());
        claims.put("roles", u.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}