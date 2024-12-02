package com.project1.orderproject.ServiceImplementation;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.project1.orderproject.POJOs.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JWTService {

    //So  the   JWT  implemented is  the   Single  key  means  about  that  this is  the   single  Key  one  a
    @Autowired
    @Lazy
    private  UserServiceImpl userServiceImpl;
    private  SecretKey  Secret_Key;

    public  JWTService() {
       
        Secret_Key_Genrate();
    }
        
            private void Secret_Key_Genrate() {
                // TODO Auto-generated method stub
                try {
                    KeyGenerator  keyGenerator =  KeyGenerator.getInstance("HmacSHA256");
                    Secret_Key =  keyGenerator.generateKey();
                } catch (NoSuchAlgorithmException e) {
                    // TODO Auto-generated catch block
                    //you  can  Ctah  the   Epxpwio     
                    e.printStackTrace();
                }
            } 
    public  String  genrateJwtToken(String  user_id) {
        Map<String,Object> map  =  new  HashMap<>();
        return  Jwts.builder()
                    .subject(user_id)
                    .id(UUID.randomUUID().toString())
                    .issuedAt(new  Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+(1000*60)))
                    .claims(map)
                    .signWith(Secret_Key).compact();
                    
    }
    public String  getUserId(String token) {
        final  Claims  claims  =  Jwts
                                        .parser()
                                        .verifyWith(Secret_Key)
                                        .build()
                                        .parseSignedClaims(token)
                                        .getPayload();
        log.info(claims.getSubject()+"   "+ claims.getExpiration());
        return claims.getSubject();
    }

    public boolean validate(User user, String token) {
        // TODO Auto-generated method stub
        String  token_userId  = getUserId(token);
        return  ((user.getId().equals(token_userId)) &&   !isTokenExpired(token));  
    }
    private  boolean  isTokenExpired(String  token) {
        final  Claims  claims  =  Jwts
                                    .parser()
                                    .verifyWith(Secret_Key)
                                    .build()
                                    .parseSignedClaims(token)
                                    .getPayload();
        log.info(claims.getSubject()+"   "+ claims.getExpiration());
        return  claims.getExpiration().before(new  Date());

    }

            



}
