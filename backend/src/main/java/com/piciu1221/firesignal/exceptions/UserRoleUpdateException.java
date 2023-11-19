package com.piciu1221.firesignal.exceptions;

public class UserRoleUpdateException extends Exception {

    public UserRoleUpdateException(String message) {
        super(message);
    }

    public UserRoleUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}