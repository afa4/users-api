package com.users.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Phone> phones;

    public void setPhones(List<Phone> phones) {
        phones.forEach(phone -> phone.setUser(this));
        this.phones = phones;
    }
}
