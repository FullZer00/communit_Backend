package com.example.communitapi.entities.exceptions;

public class ResourceMappingException extends RuntimeException{

    public ResourceMappingException(String message) {
        super(message);
    }

    public ResourceMappingException(Exception ex) {
        super(ex);
    }

}
