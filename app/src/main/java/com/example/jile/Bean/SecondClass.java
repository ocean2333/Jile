package com.example.jile.Bean;

public class SecondClass {
    private String uuid;
    private String type;
    private String firstclass;
    private String name;
    public SecondClass(){

    }
    public SecondClass(String uuid, String type, String firstclass, String name) {
        this.uuid = uuid;
        this.type = type;
        this.firstclass = firstclass;
        this.name = name;
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
