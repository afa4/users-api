package com.users.api.controller;

import com.users.api.exception.EmailAlreadyExistsException;
import com.users.api.exception.ExpiredTokenException;
import com.users.api.exception.UnauthorizedException;
import com.users.api.model.dto.MessageDTO;
import com.users.api.model.dto.UserCreatedDTO;
import com.users.api.model.dto.UserDTO;
import com.users.api.service.AuthenticationService;
import com.users.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity create(@RequestBody UserDTO userDTO, @RequestHeader(value = "Authorization") String authorization) {
        try {
            authenticationService.validateAuthorization(authorization);
            var user = userService.create(userDTO);
            var response = new UserCreatedDTO(user.getUuid(),
                    user.getCreated(),
                    user.getModified(),
                    user.getLastLogin(),
                    user.getToken());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UnauthorizedException | ExpiredTokenException e) {
            return new ResponseEntity(new MessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
