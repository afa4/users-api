package com.users.api.controller;

import com.users.api.exception.EmailOrPasswordIncorrectException;
import com.users.api.model.dto.LoginDTO;
import com.users.api.model.dto.MessageDTO;
import com.users.api.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity login(LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(loginService.login(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (EmailOrPasswordIncorrectException e) {
            return ResponseEntity.badRequest().body(new MessageDTO(e.getMessage()));
        }
    }
}
