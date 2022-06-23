package com.alkemy.disney.disney.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class MovieDTO {

    private String id;

    @NotBlank(message = "The image url must not be empty")
    @NotNull(message = "The image url must not be null")
    private String image;

    @NotBlank(message = "The title must not be empty")
    @NotNull(message = "The title must not be null")
    @Size(max = 100, message = "The title must be no longer than 100 characters")
    private String title;

    @NotNull(message = "The creation date must not be null")
    private String creationDate;

    @Min(value = 1, message = "The rating must not be less than 1")
    @Max(value = 5, message = "The rating must not be greater than 5")
    private Float rating;

    //ACA no es necesario enviar la lista de personajes..
    @Valid
    @NotNull(message="The Characters list must not be null")
    private List<CharacterDTO> characters;

    private GenderDTO gender;

    private String genderId;
}
