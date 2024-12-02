package com.project1.orderproject.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.orderproject.POJOs.Invoice;
import com.project1.orderproject.POJOs.Orders;

import java.util.Optional;


@Repository
public interface InvoiceRepositry extends JpaRepository<Invoice,String> {

    Optional<Invoice> findByOrders(Orders orders);

}
