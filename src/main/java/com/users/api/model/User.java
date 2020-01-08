package com.users.api.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String name;
    private String email;
    private String password;
    private String token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Phone> phones;

    public void setPhones(List<Phone> phones) {
        phones.forEach(phone -> phone.setUser(this));
        this.phones = phones;
    }
}
