package com.example.todoapp;

public class ToDo {
    private String Name;
    private String ChiTiet;
    private String Id;

    public ToDo(String name, String chiTiet, String id) {
        Name = name;
        ChiTiet = chiTiet;
        Id = id;
    }

    public ToDo(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getChiTiet() {
        return ChiTiet;
    }

    public void setChiTiet(String chiTiet) {
        ChiTiet = chiTiet;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
