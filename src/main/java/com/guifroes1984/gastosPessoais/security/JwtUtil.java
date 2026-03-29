package com.guifroes1984.gastosPessoais.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET = "9f8a7b6c5d4e3f2g1h0i!@#$%^&*()_+ABCDEFGHijklmnop";
	
	private Key getChave() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	public String gerarToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
            .compact();
    }
	
	public String extrairEmail(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
	
	public boolean isTokenValido(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(getChave())
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
