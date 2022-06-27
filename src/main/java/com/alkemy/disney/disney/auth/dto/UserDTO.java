package com.alkemy.disney.disney.auth.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @Email(message = "Username must be an email")
    @NotNull(message = "Username must not be null")
    private String username;

    @NotBlank(message = "Password must have no spaces")
    @NotNull(message = "Password must not be null")
    @Size(min=8, max=16, message = "The password must be between 8 and 16 characters Long")
    private String password;
}
