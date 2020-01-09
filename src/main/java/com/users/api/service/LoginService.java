package com.users.api.service;

import com.users.api.exception.EmailOrPasswordIncorrectException;
import com.users.api.model.dto.UserCreatedDTO;
import com.users.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public UserCreatedDTO login(String email, String password) throws EmailOrPasswordIncorrectException {
        var optionalUser = userRepository.findByEmailAndPassword(email, password);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            return new UserCreatedDTO(
                    user.getUuid(),
                    user.getCreated(),
                    user.getModified(),
                    user.getLastLogin(),
                    user.getToken());
        }
        throw new EmailOrPasswordIncorrectException();
    }


}
