package com.example.maru.despro;

public class User {
    public String Name, Email,Cp, Age,Password;

    public User(){

    }

    public User(String name,String age, String cp, String email,String password){
        this.Name= name;
        this.Age=age;
        this.Cp=cp;
        this.Email=email;
        this.Password=password;
    }
}
