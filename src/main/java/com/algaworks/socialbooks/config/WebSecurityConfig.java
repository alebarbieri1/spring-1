package com.algaworks.socialbooks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Anotação obrigatória para habilitar a segurança
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	// Como vai ser a autenticação: banco de dados, memória...
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("algaworks").password("senha").roles("USER");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		// Utiliza o método básico de autenticação http
		http.authorizeRequests()
		// Não vai requisitar autenticação nessa URI
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and()
			.httpBasic()
		.and()
			.csrf().disable() // CSRF: tipo de proteção para evitar ataques
		.headers().cacheControl().disable();  // Desabilita as configurações de cache do spring security
	}
}
