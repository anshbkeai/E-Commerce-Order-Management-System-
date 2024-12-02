package com.project1.orderproject.ExceptionHandler;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String HttpStatus;
    private  String message;
    private  Map<String,Object>  Error_object;

}
