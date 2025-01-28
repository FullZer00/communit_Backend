package com.example.communitapi.entities.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ResourceMappingException extends RuntimeException {

    public ResourceMappingException(String message) {
        super(message);
    }

    public ResourceMappingException(Exception ex) {
        super(ex);
    }

    // Метод для получения места ошибки
    public Map<String, String> getErrorLocation() {
        StackTraceElement[] stackTrace = this.getStackTrace();
        Map<String, String> errorLocation = new HashMap<>();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            errorLocation.put("Ошибка " + String.valueOf(i),
                    "Ошибка в классе: " + element.getClassName() +
                            ", метод: " + element.getMethodName() +
                            ", строка: " + element.getLineNumber());
        }
        return errorLocation;
    }

}
