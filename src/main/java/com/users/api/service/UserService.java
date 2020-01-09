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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Value("${jwt.secret}")
    private String SECRET;

    public User create(UserDTO userDTO) throws EmailAlreadyExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        Date creationDate = new Date();
        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
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
