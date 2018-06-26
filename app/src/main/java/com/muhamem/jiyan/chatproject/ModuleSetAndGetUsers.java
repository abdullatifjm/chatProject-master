package com.muhamem.jiyan.chatproject;

public class ModuleSetAndGetUsers {
    String password, name , color;

    public ModuleSetAndGetUsers(String password, String name , String color) {
        this.password = password;
        this.name = name;
        this.name = color;
    }
    public ModuleSetAndGetUsers(String name){
        this.name = name;
    }
    public ModuleSetAndGetUsers(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
