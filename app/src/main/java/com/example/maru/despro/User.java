package com.example.maru.despro;

public class User {
    public String Name, Email, Cp, Age, Password, Verified, Bedroom, Kitchen, LivingRoom, Toilet, Emergency;


    public User(String name, String age, String cp, String email, String password, String verified, String bedroom, String kitchen, String livingRoom, String toilet, String emergency) {
        this.Name = name;
        this.Age = age;
        this.Cp = cp;
        this.Email = email;
        this.Password = password;
        this.Verified = verified;
        this.Bedroom = bedroom;
        this.Kitchen = kitchen;
        this.LivingRoom = livingRoom;
        this.Toilet = toilet;
        this.Emergency = emergency;
    }
}
