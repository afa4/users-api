package com.users.api.service;

import com.users.api.exception.EmailAlreadyExistsException;
import com.users.api.exception.UserNotFoundException;
import com.users.api.model.Phone;
import com.users.api.model.User;
import com.users.api.model.dto.UserDTO;
import com.users.api.repository.UserRepository;
import com.users.api.util.JwtUtil;
import com.users.api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public User create(UserDTO userDTO) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        Date creationDate = new Date();
        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .token(jwtUtil.generateToken(userDTO.getEmail(), userDTO.getName(), creationDate))
                .created(creationDate)
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(PasswordUtil.encrypt(userDTO.getPassword()))
                .build();
        List<Phone> phones = userDTO.getPhones().stream()
                .map(dto -> new Phone(dto.getDdd(), dto.getNumber()))
                .collect(Collectors.toList());
        user.setPhones(phones);

        return userRepository.save(user);
    }

    public User findByToken(String token) throws UserNotFoundException {
        return userRepository.findByToken(token).orElseThrow(UserNotFoundException::new);
    }

}
