package com.project1.orderproject.POJOs;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Builder
public class Refresh_Token {

    public Refresh_Token() {
        //TODO Auto-generated constructor stub
    }

    @Id
    private  String  token_id;

    private Date date;

    private Long  refershed;

    @OneToOne
    @JoinColumn(name = "User_Id")
    private  User user;

}
