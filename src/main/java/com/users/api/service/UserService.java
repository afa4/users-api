package com.users.api.service;

import com.google.common.hash.Hashing;
import com.users.api.exception.EmailAlreadyExistsException;
import com.users.api.model.Phone;
import com.users.api.model.User;
import com.users.api.model.dto.UserDTO;
import com.users.api.repository.UserRepository;
import org.hibernate.type.UUIDBinaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(UserDTO userDTO) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .token(UUID.randomUUID().toString())
                .created(LocalDateTime.now())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(encrypt(userDTO.getPassword()))
                .build();
        List<Phone> phones = userDTO.getPhones().stream()
                .map(dto -> new Phone(dto.getDdd(), dto.getNumber()))
                .collect(Collectors.toList());
        user.setPhones(phones);

        return userRepository.save(user);
    }

    private String encrypt(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

}
