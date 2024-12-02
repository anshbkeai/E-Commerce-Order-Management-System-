package com.project1.orderproject.Filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project1.orderproject.Constants.AppUriConstant;
import com.project1.orderproject.POJOs.User;
import com.project1.orderproject.ServiceImplementation.JWTService;
import com.project1.orderproject.ServiceImplementation.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
   
  
    private final  UserServiceImpl userServiceImpl;

   private  JWTService jwtService;

    
    public  JwtFilter(JWTService  jwtService,UserServiceImpl userServiceImpl) {
        this.jwtService = jwtService;
        this.userServiceImpl=userServiceImpl;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String  requestUri =  request.getRequestURI();
        if(AppUriConstant.CONTANT_URI.contains(requestUri) || requestUri.startsWith("/test/") || requestUri.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token="";String  userid =  "";
        String authheader =  request.getHeader("Authorization");
        if(authheader != null && authheader.startsWith("Bearer ")) {
                token  =  authheader.substring(7);
                userid  =  jwtService.getUserId(token);

        }
        if(userid != null  &&  SecurityContextHolder.getContext().getAuthentication() == null) {
            User  user = userServiceImpl.loadUserById(userid);
                if (jwtService.validate(user, token)) {
                    UsernamePasswordAuthenticationToken authToken = new  UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
    
                    logger.info("Authentication successful for user: " + userid);
            }
            else {
                logger.info("Failed  to  validate");
            }
        }

        filterChain.doFilter(request, response);


    }

}
