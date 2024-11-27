package com.finalproject.finalproject.enums;

public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private String name;

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
