package com.project1.orderproject.Service;

import org.springframework.stereotype.Service;

import com.project1.orderproject.POJOs.Invoice;
import com.project1.orderproject.POJOs.Orders;

@Service
public interface InvoicePdfGenrate {
    byte[] generat_invoice_pdf(String  order_id) ;

    Invoice  get_invoice(String  Order_id);

    Orders get_Order(String  Order_id);

}
