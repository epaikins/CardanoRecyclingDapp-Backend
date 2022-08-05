package com.cardanorecyclingdapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponseWithObject {
    protected String timestamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected Object data;
}