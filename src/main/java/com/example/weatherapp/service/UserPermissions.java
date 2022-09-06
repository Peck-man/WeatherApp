package com.example.weatherapp.service;

public enum UserPermissions {

    USER_DELETE("user:delete"),
    CITY_CREATE("city:create"),
    CITY_DELETE("city:delete");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
