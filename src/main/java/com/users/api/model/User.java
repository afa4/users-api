package com.users.api.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private String uuid;
    private String name;
    private String email;
    private String password;
    private String token;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modified;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Phone> phones;

    public void setPhones(List<Phone> phones) {
        phones.forEach(phone -> phone.setUser(this));
        this.phones = phones;
    }
}
