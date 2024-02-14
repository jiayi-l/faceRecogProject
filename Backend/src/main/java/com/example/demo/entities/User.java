package com.example.demo.entities;

public class User {
    String name;
    String ID;
    String email;

    public User(String n, String id, String email){
        name =n;
        ID = id;
        this.email = email;
    }
    
    public void setName(String n){
        name = n;
    }

    public void setID(String id){
        ID = id;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return ID;
    }

    public String getEmail(){
        return email;
    }
}
