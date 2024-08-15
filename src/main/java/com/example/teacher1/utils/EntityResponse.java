package com.example.teacher1.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityResponse<T>{
    private Integer status;

   private String message;
   private T entity;
}
