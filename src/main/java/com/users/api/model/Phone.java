package com.users.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
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

    public Phone(String ddd, String number) {
        this.ddd = ddd;
        this.number = number;
    }
}
