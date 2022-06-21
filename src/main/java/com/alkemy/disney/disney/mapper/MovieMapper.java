package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MovieMapper {
    private CharacterMapper characterMapper;
    private GenderMapper genderMapper;

    public MovieMapper(@Lazy CharacterMapper characterMapper, GenderMapper genderMapper) {
        this.characterMapper = characterMapper;
        this.genderMapper = genderMapper;
    }

    //MAPPER TO PASS FROM DTO MOVIE TO ENTITY
    public MovieEntity movieDTO2Entity (MovieDTO movieDTO){
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setCreationDate(
                this.string2LocalDate(movieDTO.getCreationDate())
        );
        movieEntity.setRating(movieDTO.getRating());
        List<CharacterEntity> characters = this.characterMapper.DTOList2CharacterEntityList(movieDTO.getCharacters());
        movieEntity.setCharacters(characters);
        movieEntity.setGenderId(movieDTO.getGenderId());
        return movieEntity;
    }
    // MAPPER TO PASS FROM DTO MOVIE TO ENTITY
    public MovieDTO movieEntity2DTO (MovieEntity movieEntity, Boolean loadCharacters, Boolean loadGenders){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setCreationDate(
                this.localDate2String(movieEntity.getCreationDate())
        );
        movieDTO.setRating(movieEntity.getRating());
        if (loadCharacters){
            // CAST
            List<CharacterDTO>characterDTOS = characterMapper.characterEntityList2DTOList(movieEntity.getCharacters(), false);
            movieDTO.setCharacters(characterDTOS);
        }
        if(loadGenders) {
            movieDTO.setGender(this.genderMapper.genderEntity2DTO(movieEntity.getGender())); //consultar
        }
        return movieDTO;
    }
    private LocalDate string2LocalDate(String stringDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }
    // FORMATTING OF LOCALDATE TO STRING
    private String localDate2String(LocalDate localDate) {

        String formatDate = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        return formatDate;
    }
    public List<MovieDTO> movieEntityList2DTOList (Collection<MovieEntity> movieEntities,Boolean loadCharacters, Boolean loadGenders){
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (MovieEntity movieEntity: movieEntities) {
            movieDTOS.add(this.movieEntity2DTO(movieEntity,loadCharacters,loadGenders));

        }
        return movieDTOS;
    }
    public void movieEntityRefreshValues(MovieEntity movieEntity,MovieDTO movieDTO){
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setCreationDate(this.string2LocalDate(movieDTO.getCreationDate()));
        movieEntity.setRating(movieDTO.getRating());
        movieEntity.setGenderId(movieDTO.getGenderId());
    }
    public MovieBasicDTO movieBasicEntity2BasicDTO(MovieEntity movieEntity){
        MovieBasicDTO movieBasicDTO = new MovieBasicDTO();
        movieBasicDTO.setId(movieEntity.getId());
        movieBasicDTO.setImage(movieEntity.getImage());
        movieBasicDTO.setTitle(movieEntity.getTitle());
        movieBasicDTO.setCreationDate(movieEntity.getCreationDate().toString());
        return movieBasicDTO;

    }
    public List<MovieBasicDTO> movieEntityBasicList2DTOBasicList(List<MovieEntity>entities){
        List<MovieBasicDTO>dtos = new ArrayList<>();
        for (MovieEntity entity:entities) {
            dtos.add(this.movieBasicEntity2BasicDTO(entity));
        }
        return dtos;
    }

}
