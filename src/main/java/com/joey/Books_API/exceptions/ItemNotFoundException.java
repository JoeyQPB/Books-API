package com.joey.Books_API.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException (String msg) {
        super(msg);
    }
}
