package com.project1.orderproject.Contoller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.orderproject.DTO.OrderDto;
import com.project1.orderproject.ServiceImplementation.InvoiceGenrateImplementation;
import com.project1.orderproject.ServiceImplementation.JWTService;
import com.project1.orderproject.ServiceImplementation.OrderServiceImplementaion;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private  OrderServiceImplementaion orderServiceImplementaion;

    @Autowired
    private  InvoiceGenrateImplementation invoiceGenrateImplementation;

    @Autowired
    private  JWTService jwtService;

    @PostMapping("/place")
    public ResponseEntity<Object> postMethodName(@RequestBody Map<String,Integer> map,HttpServletRequest request) {
        OrderDto  orderDto =  new OrderDto();
        orderDto.setProduct_Map(map);
        log.info("Received OrderDto: {}", orderDto);
        if (orderDto.getProduct_Map() == null) {
                log.error("Product_Map is null");
        }
        String s  = request.getHeader("Authorization");
        log.info("in the  order  Controller   Get me  the   Token  = {}",s);
        String  user_id  = jwtService.getUserId(s.substring(7));
        orderServiceImplementaion.placeOrder(orderDto, user_id);
        
        ResponseEntity.status(HttpStatus.ACCEPTED).body("The  Order  is  Placed  Hurray ");
        log.info("Hey  Passed ", user_id);
        log.info("The   IS ",orderDto.getProduct_Map().get("P001"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{order_id}/invoice")
    public ResponseEntity<byte[]> getMethodName(@PathVariable String order_id) {

        byte[] pdfBytes = invoiceGenrateImplementation.generat_invoice_pdf(order_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice_" + order_id + ".pdf");
        return  ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
    
    

}
