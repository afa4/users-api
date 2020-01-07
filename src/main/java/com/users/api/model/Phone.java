package com.users.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String ddd;
    private String number;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
