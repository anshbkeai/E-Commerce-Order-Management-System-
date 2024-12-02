package com.project1.orderproject.Service;

import org.springframework.stereotype.Service;

import com.project1.orderproject.DTO.OrderDto;
import com.project1.orderproject.POJOs.Orders;

@Service
public interface OrderService {
    void placeOrder(OrderDto orderDto, String  userid);
    
    void  cancelOrder(String  Orderid,String Userid);

    Orders get_Order_From_Dto(OrderDto orderDto,String  user_id,Double total_amount);

    //need  a  method  to  get teh  invoice   . but  Not  making itt  as we   donot  have   the   Invoice  Class   to  it;
}
