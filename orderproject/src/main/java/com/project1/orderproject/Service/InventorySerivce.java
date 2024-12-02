package com.project1.orderproject.Service;

import org.springframework.stereotype.Service;

import com.project1.orderproject.DTO.OrderDto;

@Service
public interface InventorySerivce {
    Double  check_Product_Vailidity(OrderDto orderDto,String  user_id);
    /*
     * when  we   scale   our app  we  will  definr  more   MEthods   here  about form  te  ROLES 
     */
}
