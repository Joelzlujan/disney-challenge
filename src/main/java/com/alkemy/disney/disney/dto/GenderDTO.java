package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class GenderDTO {

    private String id;

    @NotBlank(message = "The name must not be empty")
    @NotNull(message = "The name must not be null")
    @Size(max = 100, message = "The name must be no longer than 100 characters")
    private String name;

    @NotBlank(message = "The image url must not be empty")
    @NotNull(message = "The image url must not be null")
    private String image;

}
