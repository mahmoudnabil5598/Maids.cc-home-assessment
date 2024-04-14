package com.example.demo.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Object id) {

        super("The " + entityName + " with identifier '" + id.toString() + "' does not exist in our records");
    }

}