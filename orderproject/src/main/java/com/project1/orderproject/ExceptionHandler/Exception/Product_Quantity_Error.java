package com.project1.orderproject.ExceptionHandler.Exception;

public class Product_Quantity_Error extends RuntimeException{
    private String  product_id;
    private Integer  recived_quantity;
    private Integer  actual_quantity;
    private  String  User_Id;
    public String getProduct_id() {
        return product_id;
    }
    public Integer getRecived_quantity() {
        return recived_quantity;
    }
    public Integer getActual_quantity() {
        return actual_quantity;
    }
    public String getUser_Id() {
        return User_Id;
    }
    public  Product_Quantity_Error(String mesaage,String pid,Integer recived_quantity,Integer actual_quantity,String  User_Id) {
        super(mesaage);
        this.product_id=  pid;
        this.actual_quantity= actual_quantity;
        this.recived_quantity =  recived_quantity;
        this.User_Id    =   User_Id;
    }

}
