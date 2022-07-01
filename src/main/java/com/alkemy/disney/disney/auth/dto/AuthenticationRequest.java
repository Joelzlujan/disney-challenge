package com.alkemy.disney.disney.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class AuthenticationRequest {
    @NotBlank(message = "The Username must not be empty")
    @NotNull(message = "The Username must not be null")
    private String username;

    @NotBlank(message = "The password must not be empty")
    @NotNull(message = "The password must not be null")
    private String password;
}
