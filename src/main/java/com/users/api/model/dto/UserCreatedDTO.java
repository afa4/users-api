package com.users.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedDTO {
    private String id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
}