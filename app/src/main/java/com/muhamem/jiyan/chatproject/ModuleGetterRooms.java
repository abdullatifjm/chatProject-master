package com.muhamem.jiyan.chatproject;

public class ModuleGetterRooms {
    private String id ,name , pass;

    public ModuleGetterRooms(){

    }

    public ModuleGetterRooms(String id , String name, String pass) {
        this.name = name;
        this.pass = pass;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
