package com.example.todoapp;

class YeuThich {
    private String Name;
    private String ChiTiet;
    private String Id;

    public YeuThich(String name, String chiTiet, String id) {
        Name = name;
        ChiTiet = chiTiet;
        Id = id;
    }

    public YeuThich(){

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
