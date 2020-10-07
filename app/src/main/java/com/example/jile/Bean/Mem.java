package com.example.jile.Bean;

public class Mem {

    private String uuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Mem(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    private String name;


}
