package com.project1.orderproject.Service;

import com.project1.orderproject.POJOs.Orders;

public interface InvoiceService {
    void  save_invoice(Orders  order_id, String   user_id,Double  Amount);
} 
