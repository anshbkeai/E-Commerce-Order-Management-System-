package com.project1.orderproject.ServiceImplementation;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project1.orderproject.DTO.OrderDto;
import com.project1.orderproject.POJOs.Orders;
import com.project1.orderproject.Repositry.OrderRepositry;
import com.project1.orderproject.Repositry.UserRepositry;
import com.project1.orderproject.Service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImplementaion implements  OrderService {
    private  Inventory_Serivce_Implementaion inventory_Serivce_Implementaion;
    private  OrderRepositry orderRepositry;
   private  UserRepositry userRepositry;
   private  Invoice_Service_Implementaion invoice_Service_Implementaion;


    public OrderServiceImplementaion(Inventory_Serivce_Implementaion inventory_Serivce_Implementaion,
            OrderRepositry orderRepositry,
            UserRepositry userRepositry,
            Invoice_Service_Implementaion invoice_Service_Implementaion) {
        this.inventory_Serivce_Implementaion = inventory_Serivce_Implementaion;
        this.orderRepositry = orderRepositry;
        this.userRepositry  = userRepositry;
        this.invoice_Service_Implementaion =  invoice_Service_Implementaion;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class,RuntimeException.class})
    public void placeOrder(OrderDto orderDto, String userid) {
        // TODO Auto-generated method stub
        
        log.info("Going  For  the Check  in the  Inventory ");
        Double  total_amount  = inventory_Serivce_Implementaion.check_Product_Vailidity(orderDto, userid);
        Orders  order  =  get_Order_From_Dto(orderDto,userid,total_amount);
        orderRepositry.save(order);
        log.info("Going  For  the  Genrating  and  Saving  the  Invoice  with the  Order id {}",order.getId());
        invoice_Service_Implementaion.save_invoice(order, userid, total_amount);
        log.info("Invoice  saved  You  can   DO it in  the Controller to  get the   Pdf");


    }

    @Override
    public void cancelOrder(String Orderid, String Userid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelOrder'");
    }

    @Override
    public Orders get_Order_From_Dto(OrderDto orderDto,String  user_id,Double total_amount) {
        // TODO Auto-generated method stub
        Orders orders =  new Orders();
        orders.setId(UUID.randomUUID().toString());
        orders.setUser(userRepositry.findById(user_id).orElseThrow(() -> 
                   new RuntimeException("User not found for id: " + user_id)));
        orders.setAmount(total_amount);
        orders.setDate(new Date());
        orders.setProductList(orderDto.getProduct_Map());
        log.info("Thus  the   Cast of  Order is  Done "+orders.toString());
        return  orders;    
    }

}
