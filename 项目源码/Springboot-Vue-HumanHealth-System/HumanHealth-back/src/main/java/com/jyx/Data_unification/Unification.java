package com.jyx.Data_unification;

//主要用于统一返回结果格式。

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unification<T> {
    private Integer code;
    private String message;
    private T data;


    public static <T> Unification<T> success(){
        return new Unification<>(20000,"success",null);
    }

    public static <T> Unification<T> success(T data){
        return new Unification<>(20000,"success",data);
    }

    public static <T> Unification<T> success(T data,String message){
        return new Unification<>(20000,message,data);
    }

    public static <T> Unification<T> success(String message){
        return new Unification<>(20000,message,null);
    }

    public static<T>  Unification<T> fail(){
        return new Unification<>(20001,"fail",null);
    }

    public static<T>  Unification<T> fail(Integer code){
        return new Unification<>(code,"fail",null);
    }

    public static<T>  Unification<T> fail(Integer code, String message){
        return new Unification<>(code,message,null);
    }

    public static<T>  Unification<T> fail( String message){
        return new Unification<>(20001,message,null);
    }
}
