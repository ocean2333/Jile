package com.example.jile.Bean;

public class FirstClass {
    private String uuid;
    private String type;

    private String name;

    public FirstClass(String uuid, String type, String name) {
        this.uuid = uuid;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
