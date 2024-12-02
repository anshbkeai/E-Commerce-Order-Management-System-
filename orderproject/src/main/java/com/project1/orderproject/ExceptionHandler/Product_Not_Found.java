package com.project1.orderproject.ExceptionHandler;


public class Product_Not_Found extends  RuntimeException{
    private String  product_id;
    private Integer  qty;
    private  String  User_Id;
    public String getProduct_id() {
        return product_id;
    }
    public Integer getQty() {
        return qty;
    }
    public String getUser_Id() {
        return User_Id;
    }
    public  Product_Not_Found(String mesaage,String pid,Integer  Qty,String  User_Id) {
        super(mesaage);
        this.product_id=  pid;
        this.qty    =Qty;
        this.User_Id    =   User_Id;
       
    }
}
