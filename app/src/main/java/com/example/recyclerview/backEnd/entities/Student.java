package com.example.recyclerview.backEnd.entities;

public class Student {

    private long id;
    private String name;
    private int hwCount;
    private int colorArgb;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHwCount() {
        return hwCount;
    }

    public void setHwCount(int hwCount) {
        this.hwCount = hwCount;
    }

    public int getColorArgb() {
        return colorArgb;
    }

    public void setColorArgb(int colorArgb) {
        this.colorArgb = colorArgb;
    }
}