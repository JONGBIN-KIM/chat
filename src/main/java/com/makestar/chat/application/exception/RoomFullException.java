package com.makestar.chat.application.exception;

public class RoomFullException extends RuntimeException {
    public RoomFullException(String message) {
        super(message);
    }
}
