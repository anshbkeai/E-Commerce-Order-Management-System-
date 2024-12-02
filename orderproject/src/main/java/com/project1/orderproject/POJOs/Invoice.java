package com.project1.orderproject.POJOs;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Invoice {
    @Id
    private  String  invoiceId;

    private  Double amount;

    private Date invoice_date;

    @ManyToOne
    @JoinColumn(name = "User_Id",nullable =  false)
    private  User user;

    @OneToOne
    @JoinColumn(name = "Order_Id",nullable = false)
    private Orders orders;
    
}
