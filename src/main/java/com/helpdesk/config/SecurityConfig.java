package com.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.helpdesk.security.JWTAuthenticationFilter;
import com.helpdesk.security.JWTUtil;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	@Autowired
	private Environment env; // representa o ambiente que ta , por exemplo o perfil de test
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest().authenticated();// estamos permitindo tudo do h2 e tudo que depois da barra 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // asseguro que a sessao nao vai ser criada
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	// configurar os filtros, e os endpoint
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues(); //aplica um permissao padrao para nos ja libera o get e post
		configuration.setAllowedHeaders(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS")); // libera todos os metodos 
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // qual fonte que voce quer receber requisicoes ? todas simboliza /**
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();// como é um bean posso injetar em qualquer outra parte do meu codigo
	} 
	

}
