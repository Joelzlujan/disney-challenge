package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO){
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntityRefreshValues(characterEntity,characterDTO);
        return characterEntity;
    }
    public CharacterDTO characterEntity2DTO(CharacterEntity characterEntity,Boolean loadMovies){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTORefreshValues(characterDTO,characterEntity);
        //VALID IF MOVIES ARE LOADED
        if(loadMovies){
            List<MovieDTO> movieDTOList = new ArrayList<>();
            for (MovieEntity entity:characterEntity.getMovies()) {
                List<MovieDTO>movieDTOS = movieMapper.movieEntityList2DTOList(characterEntity.getMovies(),false,true);
                characterDTO.setMovies(movieDTOList);
            }

        }
        return characterDTO;

    }
    public CharacterBasicDTO characterBasicEntity2BasicDTO(CharacterEntity characterEntity){
        CharacterBasicDTO dto = new CharacterBasicDTO();
        dto.setId(characterEntity.getId());
        dto.setName(characterEntity.getName());
        dto.setImage(characterEntity.getImage());
        return dto;

    }
    public CharacterEntity characterBasicDTO2BasicEntity (CharacterBasicDTO characterBasicDTO){
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setImage(characterBasicDTO.getImage());
        characterEntity.setName(characterBasicDTO.getName());
        return characterEntity;
    }
    //Update character entity
    public void characterEntityRefreshValues (CharacterEntity characterEntity,CharacterDTO characterDTO){
        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setHistory(characterDTO.getHistory());

    }
    //Update character DTO
    public void characterDTORefreshValues(CharacterDTO characterDTO,CharacterEntity characterEntity){
        characterDTO.setId(characterEntity.getId());
        characterDTO.setImage(characterEntity.getImage());
        characterDTO.setName(characterEntity.getName());
        characterDTO.setAge(characterEntity.getAge());
        characterDTO.setWeight(characterEntity.getWeight());
        characterDTO.setHistory(characterEntity.getHistory());

    }
    public List<CharacterDTO> characterEntityList2DTOList(List<CharacterEntity> entities, Boolean loadMovies) {
        List<CharacterDTO> characterDTOS = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            characterDTOS.add(this.characterEntity2DTO(entity, loadMovies));
        }
        return characterDTOS;
    }

    public List<CharacterEntity> DTOList2CharacterEntityList(List<CharacterDTO> dtos) {
        List<CharacterEntity> characterEntities = new ArrayList();
        for (CharacterDTO dto : dtos) {
            characterEntities.add(this.characterDTO2Entity(dto));
        }
        return characterEntities;
    }
    public List<CharacterBasicDTO> characterBasicEntityList2BasicDTOList (List<CharacterEntity>entities){
        List<CharacterBasicDTO> dtos = new ArrayList<>();
        for (CharacterEntity entity:entities) {
            dtos.add(this.characterBasicEntity2BasicDTO(entity));
        }
        return dtos;
    }


}

