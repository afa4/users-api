package com.users.api.service;

import com.users.api.exception.UserExistsException;
import com.users.api.model.Phone;
import com.users.api.model.User;
import com.users.api.model.dto.UserDTO;
import com.users.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserDTO userDTO) throws UserExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(encrypt(userDTO.getPassword()))
                .build();

        List<Phone> phones = userDTO.getPhones().stream()
                .map(dto -> new Phone(dto.getDdd(), dto.getNumber()))
                .collect(Collectors.toList());

        user.setPhones(phones);
        User stored = userRepository.save(user);
    }


    private String encrypt(String password) {
        return password;
    }


}
