package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class MovieFiltersDTO {

    private String title;
    private Set<String> idGenders;
    private String order;

    public MovieFiltersDTO(String title, Set<String> idGenders, String order) {
        this.title = title;
        this.idGenders = idGenders;
        this.order = order;
    }

    public Boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public Boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}