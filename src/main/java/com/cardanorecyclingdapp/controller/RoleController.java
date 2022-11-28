package com.cardanorecyclingdapp.controller;

import com.cardanorecyclingdapp.entity.Role;
import com.cardanorecyclingdapp.model.HttpResponseWithObject;
import com.cardanorecyclingdapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/role")
    public ResponseEntity<HttpResponseWithObject> saveRole(@RequestBody Role role){
        Role new_role = roleService.saveRole(role);
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("Role Saved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("Role",
                                new_role))
                        .build());
    }

    @GetMapping("/role/pages")
    public ResponseEntity<HttpResponseWithObject> getRoles(@RequestParam Optional<Boolean> isActive,
                                                                   @RequestParam Optional<Integer>  page,
                                                                   @RequestParam Optional<Integer> size){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("Roles Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("page",
                                roleService.getRoles(
                                        isActive.orElse(true),
                                        page.orElse(0),
                                        size.orElse(10))))
                        .build());
    }

    @GetMapping("/field/role")
    public ResponseEntity<HttpResponseWithObject> getRoles(@RequestParam(name="isActive") Boolean isActive) {
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("Roles Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("roles",
                                roleService.getRoles(isActive)))
                        .build());
    }

    @GetMapping("/role")
    public ResponseEntity<HttpResponseWithObject> getRole(@RequestParam(name="id") Long id){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("Role Retrieved")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("role",
                                roleService.getRole(id)))
                        .build()
        );
    }

    @PutMapping("/role")
    public ResponseEntity<HttpResponseWithObject> editRole(@RequestParam(name="id") Long id, @RequestBody Role role){
        return ResponseEntity.ok().body(
                HttpResponseWithObject.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .message("Role Updated")
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .data(Map.of("role",
                                roleService.editRole(id, role)))
                        .build()
        );
    }

}
