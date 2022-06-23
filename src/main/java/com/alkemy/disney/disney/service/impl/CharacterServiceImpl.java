package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.exceptions.DuplicateValueException;
import com.alkemy.disney.disney.exceptions.NotFoundException;
import com.alkemy.disney.disney.mapper.CharacterMapper;
import com.alkemy.disney.disney.repository.CharacterRepository;
import com.alkemy.disney.disney.repository.MovieRepository;
import com.alkemy.disney.disney.repository.specification.CharacterSpecification;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CharacterServiceImpl implements CharacterService {

    private CharacterRepository characterRepository;
    private CharacterMapper characterMapper;
    private MovieService movieService;
    private MovieRepository movieRepository;

    private CharacterSpecification characterSpecification;


    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, CharacterMapper characterMapper, MovieService movieService, MovieRepository movieRepository, CharacterSpecification characterSpecification) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
        this.movieService = movieService;
        this.movieRepository = movieRepository;
        this.characterSpecification = characterSpecification;

    }

    @Transactional
    @Override
    public CharacterDTO save(CharacterDTO characterDTO) {
        validateCharacter(characterDTO,null);
        CharacterEntity characterEntity = this.characterMapper.characterDTO2Entity(characterDTO);
        CharacterEntity entitySaved = this.characterRepository.save(characterEntity);
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved,false);
        return result;
    }
    @Transactional
    @Override
    public CharacterDTO update(String id, CharacterDTO characterDTO) {
        CharacterEntity characterEntity = this.getEntityById(id);
        validateCharacter(characterDTO,characterEntity);
        if(this.characterRepository.existsById(id)) {
            this.characterMapper.characterEntityRefreshValues(characterEntity, characterDTO);
            CharacterEntity entitySaved = this.characterRepository.save(characterEntity);
            CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, false);
            return result;
        }else{
            throw new NotFoundException("The Character to update with id "+id + "does not exist");
        }
    }
    @Transactional
    @Override
    public void delete(String id) {
        if(this.characterRepository.existsById(id)){
            this.characterRepository.deleteById(id);
        }else {
            throw new NotFoundException("The character to delete with id "+id+" does not exist");
        }
    }
    @Transactional
    @Override
    public List<CharacterBasicDTO> getAll() {
        List<CharacterEntity> characterEntities = this.characterRepository.findAll();
        List<CharacterBasicDTO>characterDTOS = this.characterMapper.characterBasicEntityList2BasicDTOList(characterEntities);

        return characterDTOS;
    }
    @Transactional(readOnly = true)
    @Override
    public CharacterDTO getDetailsById(String id) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if(!entity.isPresent()){
            throw new NotFoundException("The character with id '"+id+"' wasn't found");
        }else {
            CharacterDTO dto = this.characterMapper.characterEntity2DTO(entity.get(),true);
            return dto;
        }

    }
    @Transactional(readOnly = true)
    @Override
    public CharacterEntity getEntityById(String id) {
        Optional<CharacterEntity>entity = this.characterRepository.findById(id);
        if(!entity.isPresent()){
            throw new NotFoundException("The character with id "+id+" does not exist");
        }
        return entity.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existsByName(String name) {
        return this.characterRepository.existByName(name);
    }
    @Transactional
    @Override
    public List<CharacterDTO> getDetailsByFilters(String name, Integer age, Set<String> idMovies, String order) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name,age,idMovies,order);
        List<CharacterEntity>entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        List<CharacterDTO>dtos = this.characterMapper.characterEntityList2DTOList(entities,true);
        return dtos;
    }
    @Override
    public void validateCharacter(CharacterDTO characterDTO, CharacterEntity characterEntity) {
        if(existsByName(characterDTO.getName()) && (characterEntity == null || !characterEntity.getName().equalsIgnoreCase(characterDTO.getName()))){
            throw new DuplicateValueException("There is already a Character with the name '"+characterDTO.getName()+"'");
        }
    }

}
