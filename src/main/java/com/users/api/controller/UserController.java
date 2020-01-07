package com.users.api.controller;

import com.users.api.exception.UserExistsException;
import com.users.api.model.dto.MessageDTO;
import com.users.api.model.dto.UserDTO;
import com.users.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity create(@RequestBody UserDTO userDTO) {
        try {
            userService.create(userDTO);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (UserExistsException e) {
            return new ResponseEntity(new MessageDTO(e.getMessage()), HttpStatus.CONFLICT);
        }

    }
}
