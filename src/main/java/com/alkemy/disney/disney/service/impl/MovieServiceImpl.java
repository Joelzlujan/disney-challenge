package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.MovieFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.exceptions.DuplicateValueException;
import com.alkemy.disney.disney.exceptions.NotFoundException;
import com.alkemy.disney.disney.exceptions.ParamNotFoundException;
import com.alkemy.disney.disney.mapper.MovieMapper;
import com.alkemy.disney.disney.repository.MovieRepository;
import com.alkemy.disney.disney.repository.specification.MovieSpecification;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.GenderService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    //private GenderService genderService;
    private MovieMapper movieMapper;
    //private MovieSpecification movieSpecification;
    private CharacterService characterService;

    private MovieSpecification movieSpecification;

    private GenderService genderService;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, @Lazy CharacterService characterService, MovieMapper movieMapper, MovieSpecification movieSpecification, GenderService genderService) {
        this.movieRepository = movieRepository;
        this.characterService = characterService;
        this.movieMapper = movieMapper;
        this.movieSpecification = movieSpecification;
        this.genderService = genderService;
    }

    @Transactional
    @Override
    public MovieDTO save(MovieDTO movieDTO) {
        validateMovie(movieDTO,null);
        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movieDTO);
        MovieEntity entitySaved = movieRepository.save(movieEntity);
        MovieDTO result = movieMapper.movieEntity2DTO(entitySaved, true,false);
        return result;
    }
    @Transactional
    @Override
    public MovieDTO update(String id, MovieDTO movieDTO) {
        MovieEntity movieEntity = this.getEntityById(id);
        validateMovie(movieDTO,movieEntity);
        if(this.movieRepository.existsById(id)) {
            this.movieMapper.movieEntityRefreshValues(movieEntity, movieDTO);
            MovieEntity entitySaved = this.movieRepository.save(movieEntity);
            MovieDTO result = movieMapper.movieEntity2DTO(entitySaved, true, false);
            return result;
        } else {
            throw new NotFoundException("The movie to update with id "+id + "does not exist");
        }
    }
    @Transactional
    @Override
    public void delete(String id) {
        if(this.movieRepository.existsById(id)){
            this.movieRepository.deleteById(id);
        }else {
            throw new NotFoundException("The movie to delete with id " + id + " does not exist");
        }
    }

    @Transactional
    @Override
    public List<MovieBasicDTO> getAll() {
        List<MovieEntity>movieEntities = movieRepository.findAll();
        List<MovieBasicDTO>movieBasicDTOS = movieMapper.movieEntityBasicList2DTOBasicList(movieEntities);
        return movieBasicDTOS;
    }

    @Transactional
    @Override
    public MovieEntity getEntityById(String id) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if(!entity.isEmpty()){
            throw new ParamNotFoundException("The Movie with id "+id+" wasn't found");
        }
        return entity.get();
    }
    @Transactional(readOnly = true)
    @Override
    public MovieDTO getDetailsById(String id) {
        Optional<MovieEntity>entity = this.movieRepository.findById(id);
        //MovieEntity entity = this.getEntityById(id);
        if(!entity.isPresent()) {
            throw new NotFoundException("Id Movie no válido" + id);
        }else {
            MovieDTO dto = this.movieMapper.movieEntity2DTO(entity.get(), false, true);
            // MovieDTO dto = this.movieMapper.movieEntity2DTO(entityLoaded,false,true);
            return dto;
        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<MovieDTO> getDetailsByFilters(String title, Set<String> idGenders, String order) {
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title,idGenders,order);
        List<MovieEntity> entities= this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDTO));
        List<MovieDTO> dtos = this.movieMapper.movieEntityList2DTOList(entities,false,false);
        return dtos;
    }


    @Transactional
    @Override
    public void addCharacter(String idMovie, String idCharacter) {
        MovieEntity movieEntity = this.getEntityById(idMovie);
        CharacterEntity characterEntity = this.characterService.getEntityById(idCharacter);
        //movieEntity.getCharacters(); no tiene nada que ver con el lazy, porque ya tengo el metodo de agregar peliculas implementado y lo llamo directamente
        movieEntity.addCharacter(characterEntity); //es mas entendible, mejor codigo, y sirve para hacer un control
        this.movieRepository.save(movieEntity);
    }
    @Transactional
    @Override
    public void removeCharacter(String idMovie, String idCharacter) {
        MovieEntity movieEntity = this.getEntityById(idMovie);
        CharacterEntity characterEntity = this.characterService.getEntityById(idCharacter);
        //movieEntity.getCharacters(); Esto es innecesario, si ya tengo el metodo de remove, no es necesario traerlo
        movieEntity.removeCharacter(characterEntity);
        this.movieRepository.save(movieEntity);
    }


    @Transactional(readOnly = true)
    @Override
    public void validateMovie(MovieDTO movieDTO, MovieEntity movieEntity) {
        if(this.movieRepository.existsByTitle(movieDTO.getTitle()) && (movieEntity == null || !movieEntity.getTitle().equalsIgnoreCase(movieDTO.getTitle()))){
            throw new DuplicateValueException("There is already a movie with the title '"+movieDTO.getTitle()+"'");
        }
    }
}
