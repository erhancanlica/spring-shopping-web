package com.mycompany.shopping.controller;

import com.mycompany.shopping.service.UserService;
import com.mycompany.shopping.service.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicUserController {

    private final Logger log = LoggerFactory.getLogger(PublicUserController.class);

    private final UserService userService;

    public PublicUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authorities")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllPublicUsers() {
        log.debug("REST request to get all public User names");
        return ResponseEntity.ok().body(userService.getAllPublicUsers());
    }
}
