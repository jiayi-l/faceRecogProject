package com.example.demo.entities;

public class Image {
    String ID;
    byte[] image;
    
    public Image(String id,byte[] image){
        ID = id;
        this.image = image;
    }

    public void setID(String id){
        ID = id;
    }

    public void setImage(byte[] img){
        image = img;
    }

    public String getID(){
        return ID;
    }

    public byte[] getImage(){
        return image;
    }
}
