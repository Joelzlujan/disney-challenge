package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CharacterDTO {

    private String id;

    @NotBlank(message = "The image url must not be empty")
    @NotNull(message = "The image url must not be null")
    private String image;

    @NotBlank(message = "The name must not be empty")
    @NotNull(message = "The name must not be null")
    @Size(max = 100, message = "The name must be no longer than 100 characters")
    private String name;

    @NotNull(message = "The age must not be empty")
    @PositiveOrZero(message = "The age must not negative")
    private Integer age;

    @NotNull(message = "The weight must not be null")
    @PositiveOrZero(message = "The weight must not be negative")
    private Double weight;

    @NotBlank(message = "The story must not be empty")
    @NotNull(message = "The story must not be null")
    @Size(max = 1000, message = "The story must be no longer than 1000 characters")
    private String history;
    private List<MovieDTO> movies;
}
