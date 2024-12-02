package com.project1.orderproject.POJOs;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


enum Category {
    Dairy,Electronics,Toy,Stationary,Kitchen,Drinks
}

@Data
@Entity
@Table(name = "Products")
public class Product {

    @Id
    private String id;

    private String  description;
    private  Double price;
    @Enumerated(EnumType.STRING)
    private Category category;

    private  Integer quantity;
}
