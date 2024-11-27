package com.finalproject.finalproject.enums;

public enum Roles {
    ADMIN("Админ"),
    MANAGER("Менеджер"),
    EMPLOYER("Курьер"),
    USER("Пользователь");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Roles(String name) {
        this.name = name;
    }
}
