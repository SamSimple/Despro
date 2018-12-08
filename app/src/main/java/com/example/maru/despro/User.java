package com.example.maru.despro;

public class User {
    public String name, email,cp, age,password;

    public User(){

    }

    public User(String name,String age, String cp, String email,String password){
        this.name= name;
        this.email=email;
        this.cp=cp;
        this.age=age;
        this.password=password;
    }
}
