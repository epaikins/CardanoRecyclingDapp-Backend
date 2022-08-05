package com.cardanorecyclingdapp.controller;

import com.cardanorecyclingdapp.dao.CustomerDAO;
import com.cardanorecyclingdapp.entity.CustomerType;
import com.cardanorecyclingdapp.model.HttpResponseWithObject;
import com.cardanorecyclingdapp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("customer")
    public ResponseEntity<HttpResponseWithObject> saveCustomer(@RequestParam("file") MultipartFile file, @RequestBody CustomerDAO customerDAO){
        Map<Object,Object> data = customerService.saveCustomer(file, customerDAO);
        if(data.get("value").equals(true)){
            return ResponseEntity.ok().body(
                    HttpResponseWithObject.builder().timestamp(LocalDateTime.now().toString())
                            .message("Customer Created Successfully")
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .data(Map.of("image_url", data.get("second")))
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
}
