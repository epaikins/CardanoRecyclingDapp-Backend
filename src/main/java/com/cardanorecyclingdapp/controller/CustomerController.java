package com.cardanorecyclingdapp.controller;

import com.cardanorecyclingdapp.model.HttpResponseWithObject;
import com.cardanorecyclingdapp.model.Login;
import com.cardanorecyclingdapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static com.cardanorecyclingdapp.utils.ResponseUtils.failedHttpResponseEntity;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<HttpResponseWithObject> saveCustomer(@RequestParam("file") MultipartFile file, @RequestParam("data") String json) throws IOException {
        Map<Object,Object> data = customerService.saveCustomer(file, json);
        if(data.get("value").equals(true)){
            return ResponseEntity.ok().body(
                    HttpResponseWithObject.builder().timestamp(LocalDateTime.now().toString())
                            .message("Customer Created Successfully")
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .data(Map.of("customer", data.get("message")))
                            .build());
        }
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder().timestamp(LocalDateTime.now().toString())
                        .message("Customer Creation Failed")
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .data(Map.of("error_message",data.get("message")))
                        .build());
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<HttpResponseWithObject> signinUser(@RequestBody Login loginCustomer) {
        Map<String, Object> response = customerService.signinCustomer(loginCustomer.getEmail(), loginCustomer.getPassword());
        if (response != null) {
            return ResponseEntity.ok().body(
                    HttpResponseWithObject.builder()
                            .timestamp(LocalDateTime.now().toString())
                            .message("User Successfully Signed In")
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .data(response)
                            .build());
        }
        return failedHttpResponseEntity("Sign In Failure");
    }
}
