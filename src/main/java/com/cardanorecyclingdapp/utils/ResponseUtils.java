package com.cardanorecyclingdapp.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<Object,Object> send(Object value, Object message){
        Map<Object,Object> sent = new HashMap<>();
        sent.put("value",value);
        sent.put("message",message);
        return sent;
    }
}
