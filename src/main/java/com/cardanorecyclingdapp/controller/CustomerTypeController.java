package com.cardanorecyclingdapp.controller;

import com.cardanorecyclingdapp.entity.CustomerType;
import com.cardanorecyclingdapp.model.HttpResponseWithObject;
import com.cardanorecyclingdapp.service.CustomerTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class CustomerTypeController {
    private final CustomerTypeService customerTypeService;

    @PostMapping("/customerType")
    public ResponseEntity<HttpResponseWithObject> saveCallType(@RequestBody CustomerType customerType){
        CustomerType new_customerType = customerTypeService.saveCustomerType(customerType);
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("CustomerType Saved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("CustomerType",
                                new_customerType))
                .build());
    }

    @GetMapping("/customerType/pages")
    public ResponseEntity<HttpResponseWithObject> getClaimTypes(@RequestParam Optional<Boolean> isActive,
                                                      @RequestParam Optional<Integer>  page,
                                                      @RequestParam Optional<Integer> size){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("CustomerTypes Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("page",
                                customerTypeService.getCustomerTypes(
                                        isActive.orElse(true),
                                        page.orElse(0),
                                        size.orElse(10))))
                        .build());
    }

    @GetMapping("/field/customerType")
    public ResponseEntity<HttpResponseWithObject> getClaimTypes(@RequestParam(name="isActive") Boolean isActive) {
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("CustomerTypes Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("customerTypes",
                                customerTypeService.getCustomerTypes(isActive)))
                        .build());
    }

    @GetMapping("/customerType")
    public ResponseEntity<HttpResponseWithObject> getClaimType(@RequestParam(name="id") Long id){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("CustomerType Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("customerType",
                                customerTypeService.getCustomerType(id)))
                        .build()
        );
    }

    @PutMapping("/customerType")
    public ResponseEntity<HttpResponseWithObject> editClaimType(@RequestParam(name="id") Long id, @RequestBody CustomerType customerType){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("CustomerType Updated")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("customerType",
                                customerTypeService.editCustomerType(id, customerType)))
                        .build()
        );
    }
}
