package com.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.expiration}")
	private Long expiration;
	
	@Value("${jwt.secret}")
	private String secret;
	
	//criar o metodo que vai gerar o token para nos
	// para gerar o token preciso do email
	public String generateToken(String email) {
		return Jwts.builder() // definir alguns valores para nosso token
				.setSubject(email)                       // informacoes do token vai esta aqui
				.setExpiration(new Date(System.currentTimeMillis() + expiration))                     // data atual mais aquela que setamos do application.properties
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())      // aqui coloco o metodo de embaralhar e a chave secreta
				.compact();               // deixa mais performatico
	}
}
