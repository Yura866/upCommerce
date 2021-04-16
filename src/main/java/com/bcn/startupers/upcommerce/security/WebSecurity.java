package com.bcn.startupers.upcommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bcn.startupers.upcommerce.security.jwt.JwtEntryPoint;
import com.bcn.startupers.upcommerce.security.jwt.JwtTokenFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
		@Autowired
		private UserDetailsServiceImpl userDetailsServiceImpl;

	    @Autowired
	    private JwtEntryPoint jwtEntryPoint;

	    @Bean
	    JwtTokenFilter jwtTokenFilter(){
	        return new JwtTokenFilter();
	    }

	    @Bean
	    PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Override
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable()
	                .authorizeRequests()
	                .antMatchers("/api/auth/**").permitAll()
	                .antMatchers("/api/places/**").permitAll()
	                .antMatchers("/api/user/**").permitAll()
	                .antMatchers("/h2-console/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	                .headers().frameOptions().disable() // enables the h2 database
	                .and()
	                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
	

}
