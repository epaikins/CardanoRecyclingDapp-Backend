package com.cardanorecyclingdapp.controller;

import com.cardanorecyclingdapp.entity.IdentityType;
import com.cardanorecyclingdapp.model.HttpResponseWithObject;
import com.cardanorecyclingdapp.service.IdentityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class IdentityTypeController {
    private final IdentityTypeService identityTypeService;

    @PostMapping("/identityType")
    public ResponseEntity<HttpResponseWithObject> saveCallType(@RequestBody IdentityType identityType){
        IdentityType new_identityType = identityTypeService.saveIdentityType(identityType);
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("IdentityType Saved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("IdentityType",
                                new_identityType))
                        .build());
    }

    @GetMapping("/identityType/pages")
    public ResponseEntity<HttpResponseWithObject> getClaimTypes(@RequestParam Optional<Boolean> isActive,
                                                                @RequestParam Optional<Integer>  page,
                                                                @RequestParam Optional<Integer> size){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("IdentityTypes Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("page",
                                identityTypeService.getIdentityTypes(
                                        isActive.orElse(true),
                                        page.orElse(0),
                                        size.orElse(10))))
                        .build());
    }

    @GetMapping("/field/identityType")
    public ResponseEntity<HttpResponseWithObject> getClaimTypes(@RequestParam(name="isActive") Boolean isActive) {
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("IdentityTypes Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("identityTypes",
                                identityTypeService.getIdentityTypes(isActive)))
                        .build());
    }

    @GetMapping("/identityType")
    public ResponseEntity<HttpResponseWithObject> getClaimType(@RequestParam(name="id") Long id){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("IdentityType Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("identityType",
                                identityTypeService.getIdentityType(id)))
                        .build()
        );
    }

    @PutMapping("/identityType")
    public ResponseEntity<HttpResponseWithObject> editClaimType(@RequestParam(name="id") Long id, @RequestBody IdentityType identityType){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("IdentityType Updated")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("identityType",
                                identityTypeService.editIdentityType(id, identityType)))
                        .build()
        );
    }
}
