package com.guifroes1984.gastosPessoais.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {

			String token = header.replace("Bearer ", "");

			if (jwtUtil.isTokenValido(token)) {

				Claims claims = jwtUtil.extrairClaims(token);

				String email = claims.getSubject();

				List<?> rolesRaw = claims.get("roles", List.class);

				List<GrantedAuthority> authorities = new ArrayList<>();

                if (rolesRaw != null) {
                    authorities = rolesRaw.stream()
                            .map(Object::toString)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                }

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
						null, authorities);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}
}
