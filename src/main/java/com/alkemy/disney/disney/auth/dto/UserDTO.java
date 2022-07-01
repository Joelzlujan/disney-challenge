package com.alkemy.disney.disney.auth.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    @Email(message = "Username must be an email")
    @NotNull(message = "Username must not be null")
    private String username;

    @NotBlank(message = "Password must have no spaces")
    @NotNull(message = "Password must not be null")
    private String password;
}
