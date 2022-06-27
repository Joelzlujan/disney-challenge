package com.alkemy.disney.disney.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    private String username;
    private String password;
}
