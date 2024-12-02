package com.project1.orderproject.ServiceImplementation;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project1.orderproject.DTO.OrderDto;
import com.project1.orderproject.ExceptionHandler.Product_Not_Found;
import com.project1.orderproject.ExceptionHandler.Exception.Product_Quantity_Error;
import com.project1.orderproject.POJOs.Product;
import com.project1.orderproject.Repositry.InventoryRepositry;
import com.project1.orderproject.Service.InventorySerivce;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Inventory_Serivce_Implementaion implements InventorySerivce{
    private   InventoryRepositry inventoryRepositry;
    public  Inventory_Serivce_Implementaion(InventoryRepositry inventoryRepositry){
        this.inventoryRepositry=  inventoryRepositry;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
                            rollbackFor = {Product_Not_Found.class,Product_Quantity_Error.class}
                            ,isolation = Isolation.REPEATABLE_READ)
    public Double check_Product_Vailidity(OrderDto orderDto,String  user_id) {
        // TODO Auto-generated method stub

        Double  total_amount  = 0.0;
       Map<String,Integer> map =  orderDto.getProduct_Map();
       for(String   product_id : map.keySet()) {
            Optional<Product>  optional   =  inventoryRepositry.findById(product_id);
            if(!optional.isPresent()) {
                //just   Throw  me the   Execption
                log.error("Product Dose  Not  Exist ", Product_Not_Found.class);
                throw  new Product_Not_Found("Product Dose  Not  Exist ", product_id,
                                                             map.get(product_id), user_id);
            }
            if(optional.get().getQuantity() < map.get(product_id)) {
                //Throw  a   good  respone 
                log.error("The  Error MisMatch  with  the  Product  ", Product_Quantity_Error.class);
                throw  new Product_Quantity_Error("The  Error MisMatch  with  the  Product",
                                     product_id , map.get(product_id)  , optional.get().getQuantity(), user_id);
                    
            }
           
        }
        for(String  product_id :map.keySet()) {
            Optional<Product>  optional   =  inventoryRepositry.findById(product_id);
            total_amount+=(optional.get().getPrice() * map.get(product_id));
            optional.get().setQuantity(optional.get().getQuantity()-map.get(product_id));
            inventoryRepositry.save(optional.get());
        }
        log.info("Inventory got updated. Going for the order to get saved. Total amount for the user is {}", total_amount);

        return  total_amount;
    }

}
