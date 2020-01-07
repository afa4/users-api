package com.users.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String email;
    private String password;
    @OneToMany
    private List<Phone> phones;
}
