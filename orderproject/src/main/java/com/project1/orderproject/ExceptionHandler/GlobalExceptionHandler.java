package com.project1.orderproject.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project1.orderproject.ExceptionHandler.Exception.OrderId_NotFOund;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Product_Not_Found.class)
    public ResponseEntity<ApiResponse> product_not_found(Product_Not_Found product_Not_Found) {
        ApiResponse  apiResponse =  new ApiResponse();
        apiResponse.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
        apiResponse.setMessage(product_Not_Found.getMessage());
        Map<String,Object>  map  =  new   HashMap<>();
        map.put("Product Id : ", product_Not_Found.getProduct_id());
        map.put("Quantity : ", product_Not_Found.getQty());
        map.put("User  Id ", product_Not_Found.getUser_Id());
        apiResponse.setError_object(map);
        return  new  ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
        
    }

    @ExceptionHandler(value = OrderId_NotFOund.class)
    public ResponseEntity<ApiResponse> order_id_not_found(OrderId_NotFOund ex) {
        ApiResponse apiResponse  =  new ApiResponse();
        apiResponse.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        apiResponse.setMessage(ex.getMessage());
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }


}
