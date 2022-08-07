package com.cardanorecyclingdapp.utils;

import com.cardanorecyclingdapp.model.HttpResponseWithObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResponseUtils {
    public static Map<Object,Object> send(Object value, Object message){
        Map<Object,Object> sent = new HashMap<>();
        sent.put("value",value);
        sent.put("message",message);
        return sent;
    }

    public static ResponseEntity<HttpResponseWithObject> successHttpResponseEntity(String message) {
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder().timestamp(LocalDateTime.now().toString())
                        .message(message)
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .build());
    }
    public static ResponseEntity<HttpResponseWithObject> failedHttpResponseEntity(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                HttpResponseWithObject.builder().timestamp(LocalDateTime.now().toString())
                        .message(message)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }
}
