package com.project1.orderproject.ServiceImplementation;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project1.orderproject.POJOs.Invoice;
import com.project1.orderproject.POJOs.Orders;
import com.project1.orderproject.Repositry.InvoiceRepositry;
import com.project1.orderproject.Repositry.UserRepositry;
import com.project1.orderproject.Service.InvoiceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Invoice_Service_Implementaion implements InvoiceService{

    
    private  UserRepositry userRepositry;
    private InvoiceRepositry  invoiceRepositry;
    
    public Invoice_Service_Implementaion(UserRepositry userRepositry,
            InvoiceRepositry invoiceRepositry) {
        this.userRepositry = userRepositry;
        this.invoiceRepositry = invoiceRepositry;
    }

    @Override
    @Transactional(propagation =  Propagation.MANDATORY,isolation =  Isolation.REPEATABLE_READ,
                    rollbackFor ={SQLException.class ,RuntimeException.class}       
    )

    public void save_invoice(Orders order, String user_id, Double Amount) {
        // TODO Auto-generated method stub
        Invoice invoice =  new Invoice();
        invoice.setAmount(Amount);
        invoice.setOrders(order);
        invoice.setUser(userRepositry.findById(user_id)
                                                .orElseThrow(()->new RuntimeException("User Id Not  Found ")));    
        invoice.setInvoice_date(new Date());
        invoice.setInvoiceId(UUID.randomUUID().toString());
        log.info("Saving  the   Invoice   having  this  {}",invoice.toString());

        invoiceRepositry.save(invoice);
    
    }

}
