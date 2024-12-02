package com.project1.orderproject.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.orderproject.POJOs.Product;

@Repository
public interface InventoryRepositry extends JpaRepository<Product,String>{

}
