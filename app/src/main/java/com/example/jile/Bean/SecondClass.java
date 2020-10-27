package com.example.jile.Bean;

public class SecondClass {
    private String uuid;
    private String type;
    private String firstclass;

    public SecondClass(String uuid, String type, String firstclass, String name, int iconId) {
        this.uuid = uuid;
        this.type = type;
        this.firstclass = firstclass;
        this.name = name;
        this.iconId = iconId;
    }

    private String name;

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    private int iconId;
    public SecondClass(){

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

    public String getFirstclass() {
        return firstclass;
    }

    public void setFirstclass(String firstclass) {
        this.firstclass = firstclass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
