package com.finalproject.finalproject.enums;

public enum WorkStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String name;

    WorkStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
