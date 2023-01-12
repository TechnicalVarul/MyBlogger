package com.myblogger.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myblogger.config.AppConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	 @Autowired
	 private JwtUtil jwtUtil;
	 
	 @Autowired
	 private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestToken = request.getHeader(AppConstants.JWT_REQUEST_KEY);
		
		String username = null;
		
		String token = null;
		
		if(requestToken != null && requestToken.startsWith(AppConstants.JWT_TOKEN_STARTER))
		{
			token = requestToken.substring(7);
			
			try {
				username = this.jwtUtil.extractUsername(token);
			} catch (IllegalArgumentException e) {
				System.out.println(AppConstants.TOKEN_NOT_FOUNND);
			}catch (ExpiredJwtException e) {
				System.out.println(AppConstants.EXPIRED_TOKEN);
			}catch (MalformedJwtException e) {
				System.out.println(AppConstants.INVALID_TOKEN);
			}
		}
		else {
			System.out.println(AppConstants.JWT_TOKEN_ERROR);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtUtil.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println(AppConstants.INVALID_TOKEN);
			}
		}else {
			System.out.println("Username is null or Contxt Holder is not null");
		}
		
		filterChain.doFilter(request, response);
	}

	
}
