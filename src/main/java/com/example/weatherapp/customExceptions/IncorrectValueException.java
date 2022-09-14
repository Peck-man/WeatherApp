package com.example.weatherapp.customExceptions;

public class IncorrectValueException extends Exception{
    public IncorrectValueException(String message) {
        super(message);
    }
}
