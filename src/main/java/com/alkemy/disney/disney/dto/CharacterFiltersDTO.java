package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CharacterFiltersDTO {
    private String name;
    private Integer age;
    private Set<String> idMovies;
    private String order;

    public CharacterFiltersDTO(String name, Integer age,Set<String>idMovies,String order){
        this.name = name;
        this.age = age;
        this.idMovies = idMovies;
        this.order = order;
    }
    public Boolean isASC(){
        return this.order.compareToIgnoreCase("ASC") == 0;
    }
    public Boolean isDESC(){
        return this.order.compareToIgnoreCase("DESC") == 0;
    }

}
