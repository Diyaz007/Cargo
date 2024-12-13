package com.finalproject.finalproject.enums;

public enum WorkStatus {
    WORKING("ACTIVE"),
    INACTIVE("INACTIVE"),
    WAITING_FLIGHT("WAITING_FLIGHT");


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
