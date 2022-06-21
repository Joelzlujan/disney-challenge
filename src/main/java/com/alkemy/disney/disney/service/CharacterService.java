package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;

import java.util.List;
import java.util.Set;

public interface CharacterService {
    public CharacterDTO save(CharacterDTO characterDTO);

    public CharacterDTO update(String id, CharacterDTO characterDTO);

    public void delete(String id);

    public List<CharacterBasicDTO> getAll();

    public CharacterDTO getDetailsById(String id);

    public CharacterEntity getEntityById(String id);

    public void validate (CharacterDTO characterDTO,CharacterEntity characterEntity);

    public Boolean existsByName(String name);

    public List<CharacterDTO> getDetailsByFilters(String name, Integer age, Set<String> idMovies, String order);
}
