package com.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpdesk.domain.dtos.CredenciaisDTO;


//quando cria uma classe que extends UsernamePasswordAuthenticationFilter o spring entender que ele vai 
//interceptar o endpoint post "/login"
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	
	//principal interface par autenticacao se ele foi valido vai retornar que deu certo autenticado
	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	//vamos pegar os valores passados na requisicao post/login e converter em credenciaisDTO
	
	
	//caso de tentantiva de autenticacao
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getSenha(), new ArrayList<>());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
					
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	//caso a autenticacao de sucesso entra aqui
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		response.setHeader("access-control-expose-headers" , "Authorization"); //resposta no header
		response.setHeader("Authorization" , "Bearer " + token); //resposta no header
	}
	
	//caso der errado a autenticacao entra aqui
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	}

	private CharSequence json() {
		long date = new Date().getTime();
		return "{"
				+ "\"timestamp\": "+ date + ", "
				+ "\"status\": 401, " 
				+ "\"error\": \"Não autorizado\", " 
				+ "\"message\": \"Email ou senha inválidos\", " 
				+ "\"path\": \"/login\"}";		
	}
	
}
