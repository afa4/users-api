package com.users.api.controller;

import com.users.api.exception.EmailAlreadyExistsException;
import com.users.api.model.dto.MessageDTO;
import com.users.api.model.dto.UserCreatedDTO;
import com.users.api.model.dto.UserDTO;
import com.users.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity create(@RequestBody UserDTO userDTO) {
        try {
            var user = userService.create(userDTO);
            var response = new UserCreatedDTO(
                    user.getId(),
                    user.getCreated(),
                    user.getModified(),
                    user.getLastLogin(),
                    user.getToken());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
