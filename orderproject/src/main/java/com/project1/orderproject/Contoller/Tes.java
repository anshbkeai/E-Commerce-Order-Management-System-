package com.project1.orderproject.Contoller;

import org.springframework.web.bind.annotation.RestController;

import com.project1.orderproject.ServiceImplementation.InvoiceGenrateImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class Tes {
    
    @Autowired
    private  InvoiceGenrateImplementation invoiceGenrateImplementation;

    @GetMapping("/test/{order_id}/invoice")
    public ResponseEntity<byte[]>  getMethodName(@PathVariable String order_id) {
       byte[] pdfBytes = invoiceGenrateImplementation.generat_invoice_pdf(order_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice_" + order_id + ".pdf");
        return  ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
    
}
