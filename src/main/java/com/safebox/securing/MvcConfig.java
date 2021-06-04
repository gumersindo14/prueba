package com.safebox.securing;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.safebox.securing.filter.FilterJwtAuthenticationFilter;
import com.safebox.securing.filter.LoginFilter;

@Configuration
public class MvcConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		PasswordEncoder codificador = PasswordEncoderFactories.createDelegatingPasswordEncoder();		
		auth.inMemoryAuthentication().withUser("daniel").password(codificador.encode("safeboxPassUser")).roles("ADMINISTRADOR");
	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
	    .csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/safebox/login").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilterBefore(new LoginFilter("/safebox/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new FilterJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

	
	
}
