package com.example.jile.Bean;

public class FirstClass {
    private String uuid;
    private String type;
    private String name;

    public FirstClass(String uuid, String type, String name, int iconId) {
        this.uuid = uuid;
        this.type = type;
        this.name = name;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    private int iconId;
    public FirstClass(){

    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
