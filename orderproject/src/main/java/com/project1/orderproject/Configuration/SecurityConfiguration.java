package com.project1.orderproject.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project1.orderproject.Filter.JwtFilter;
import com.project1.orderproject.ServiceImplementation.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Lazy
    private JwtFilter jwtFilter;
    @Lazy
    private UserServiceImpl  userServiceImpl;
    private  AppConfiguration appConfiguration;

    
    public  SecurityConfiguration(UserServiceImpl  userServiceImpl,AppConfiguration appConfiguration,JwtFilter jwtFilter) {
        this.userServiceImpl  =  userServiceImpl;
        this.jwtFilter  =  jwtFilter;
        this.appConfiguration =appConfiguration;
    }

     @Bean// As  you  should  Surround  with  the  Try  and   the   Catch  
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    

    @Bean
    public  AuthenticationProvider  daoauthenticationProvider() {
        DaoAuthenticationProvider  daoAuthenticationProvider  =  new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(appConfiguration.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userServiceImpl);
        return  daoAuthenticationProvider;
    }


    @Bean
    public  SecurityFilterChain securityFilterChain(HttpSecurity  httpSecurity) throws Exception {
//  if   you  are  Going  to  adc  th e  FIlter  addd  them  before .
        httpSecurity
        .csrf(csrf-> {
            csrf.disable();
        })
        .authorizeHttpRequests(authorizeHttpRequests-> {
            authorizeHttpRequests.requestMatchers("/user/**","/test/**","/actuator/**").permitAll();//when we   scale  use form the  App  Constans
            authorizeHttpRequests.anyRequest().authenticated();
        })
        .sessionManagement(session -> 
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter  , UsernamePasswordAuthenticationFilter.class)
        ;
        
        return  httpSecurity.build();
    }
}
