package com.project1.orderproject.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.orderproject.POJOs.User;

import java.util.Optional;

@Repository
public interface UserRepositry extends  JpaRepository<User,String>{

    public Optional<User> findByusername(String  username);
}
