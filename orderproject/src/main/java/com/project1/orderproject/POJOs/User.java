package com.project1.orderproject.POJOs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User implements UserDetails {

    @Id
    String id;
    
    String username;

    String password;

    @OneToMany(mappedBy = "user",cascade =  CascadeType.ALL)
    private  List<Orders> order_list =  new  ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Invoice> invoices_list =  new  ArrayList<>();

    @OneToOne(mappedBy =  "user")
    private Refresh_Token refresh_Token;
    /*
     * cConsidering  all  the  things  for  the   later   about  the   Get_Orde 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return  password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return  username;
    }

}
