package com.users.api.service;

import com.users.api.exception.EmailAlreadyExistsException;
import com.users.api.model.Phone;
import com.users.api.model.User;
import com.users.api.model.dto.UserDTO;
import com.users.api.repository.UserRepository;
import com.users.api.util.PasswordUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final String SECRET = "30c6ed0c-be74-41cd-9d0d-817aea4fa0c6";

    public User create(UserDTO userDTO) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        Date creationDate = new Date();
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .token(generateJwtToken(userDTO.getEmail(), userDTO.getName(), creationDate))
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

    private String generateJwtToken(String email, String name, Date creationDate) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .setIssuedAt(creationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

}
