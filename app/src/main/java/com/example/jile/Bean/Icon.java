package com.example.jile.Bean;

public class Icon {
    private String uuid;
    private String name;
    private String type;
    private int iconId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Icon(String uuid, String name, String type, int iconId) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.iconId = iconId;
    }
}
