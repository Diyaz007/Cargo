package com.finalproject.finalproject.enums;

public enum TripStatus {
    WAIT("Ожидание"),
    IN_PROGRESS("В пути"),
    FINISHED("Завершен"),
    CANCEL("Отменен");


    private String name;
    private TripStatus(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
