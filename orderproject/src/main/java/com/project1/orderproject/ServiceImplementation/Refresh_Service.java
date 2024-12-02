
package com.project1.orderproject.ServiceImplementation;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.project1.orderproject.POJOs.Refresh_Token;
import com.project1.orderproject.POJOs.User;
import com.project1.orderproject.Repositry.Refresh_Token_Repositry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Refresh_Service {

   
    private  Refresh_Token_Repositry token_Repositry;
    private  SecretKey Secret_Key;
    
    
    public Refresh_Service(Refresh_Token_Repositry token_Repositry) {
        
        this.token_Repositry = token_Repositry;
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

    public String  genrate_refresh(User  user) {
        Refresh_Token  refresh_Token_object  =  new   Refresh_Token();

        refresh_Token_object.setToken_id(UUID.randomUUID().toString());
        refresh_Token_object.setUser(user);
        refresh_Token_object.setDate(new Date(System.currentTimeMillis()+ (TimeUnit.DAYS.toMillis(2))));
        refresh_Token_object.setRefershed((long)0);

        log.info("THe   refrse  Tken  objext si  "+ refresh_Token_object.toString());
        String  refresh_token =  genrateToken(refresh_Token_object);
        log.info("The  Genrated  token  out of  the   Service  is  " +  refresh_token);
        token_Repositry.save(refresh_Token_object);
        return  refresh_token;

    }
       
    public  String  genrateToken(Refresh_Token  refresh_Token_object) {
        Map<String,Object> map  =  new  HashMap<>();
        return  Jwts.builder()
                    .subject(refresh_Token_object.getToken_id())
                    .id(UUID.randomUUID().toString())
                    .issuedAt(new  Date(System.currentTimeMillis()))
                    .expiration(refresh_Token_object.getDate())
                    .claims(map)
                    .signWith(Secret_Key).compact();
                    
    }
    public String  getTokenId(String token) {
        final  Claims  claims  =  Jwts
                                        .parser()
                                        .verifyWith(Secret_Key)
                                        .build()
                                        .parseSignedClaims(token)
                                        .getPayload();
        log.info(claims.getSubject()+"   "+ claims.getExpiration());
        return claims.getSubject();
    }

    public boolean validate(String token) {
        // TODO Auto-generated method stub
        String  token_Id  = getTokenId(token);
        Optional<Refresh_Token> optional =  token_Repositry.findById(token_Id);
        if(!optional.isPresent()|| isTokenExpired(token)) {
           return  false;
        }
        return  true;

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

    public  String  get_User_id(String  token ) {
        String token_id  =  getTokenId(token);
    Optional<Refresh_Token>  refresh_Token = token_Repositry.findById(token_id);
        if(!refresh_Token.isPresent()) {
            return null;
        }
        return  refresh_Token.get().getUser().getId();
    }

}
