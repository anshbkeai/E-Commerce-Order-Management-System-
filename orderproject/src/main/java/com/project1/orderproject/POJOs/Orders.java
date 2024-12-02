package com.project1.orderproject.POJOs;

import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

enum  Progress {
    Completed,Processing,Failed
}

@Data
@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    private   String   Id;

    private java.util.Date Date;

    private Double  amount;

    @Enumerated
    private Progress progress =  Progress.Completed;

    @ManyToOne
    @JoinColumn(name = "User_Id",nullable = false)
    private  User user;

    @ElementCollection
    @CollectionTable(name = "Product_Order"
                        ,joinColumns = @JoinColumn(name = "Order_ID")
    )
    @MapKeyColumn(name = "Product_ID")
    @Column(name = "Quantity")
    private  Map<String,Integer> ProductList;

    @OneToOne(mappedBy = "orders",cascade = CascadeType.ALL)
    private Invoice order_invoice;


}
