package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.MovieEntity;

import java.util.List;
import java.util.Set;

public interface MovieService {
    public MovieDTO save(MovieDTO movieDTO);

    public MovieDTO update(String id, MovieDTO movieDTO);

    public MovieEntity getEntityById(String id);

    public void delete(String id);

    public List<MovieBasicDTO> getAll();

    public MovieDTO getDetailsById(String id);

    public List<MovieDTO>getDetailsByFilters(String title, Set<String> idGenders, String order);

    public void validate(MovieDTO movieDTO,MovieEntity movieEntity);

    public void removeAllGender(String idGender);

    public void addCharacter(String idMovie, String idCharacter);

    public void removeCharacter(String idMovie, String idCharacter);
}
