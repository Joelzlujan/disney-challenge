package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.GenderDTO;
import com.alkemy.disney.disney.entity.GenderEntity;
import com.alkemy.disney.disney.exceptions.DuplicateValueException;
import com.alkemy.disney.disney.exceptions.NotFoundException;
import com.alkemy.disney.disney.mapper.GenderMapper;
import com.alkemy.disney.disney.repository.GenderRepository;
import com.alkemy.disney.disney.service.GenderService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GenderServiceImpl implements GenderService{

    private GenderRepository genderRepository;
    private GenderMapper genderMapper;
    private MovieService movieService;
    @Autowired
    public GenderServiceImpl(GenderRepository genderRepository, GenderMapper genderMapper, @Lazy MovieService movieService){
        this.genderRepository= genderRepository;
        this.genderMapper = genderMapper;
        this.movieService = movieService;
    }

    @Transactional
    @Override
    public GenderDTO save(GenderDTO genderDTO) {
        validateGender(genderDTO,null);
        GenderEntity entity = this.genderMapper.genderDTO2Entity(genderDTO);
        GenderEntity entitySaved = this.genderRepository.save(entity);
        GenderDTO result = this.genderMapper.genderEntity2DTO(entitySaved);

        return result;
    }
    @Transactional
    @Override
    public GenderDTO update(String id, GenderDTO genderDTO) {
        GenderEntity entity = this.getEntityById(id);
        validateGender(genderDTO,entity);
        if(this.genderRepository.existsById(id)) {
            this.genderMapper.genderEntityRefreshValues(entity, genderDTO);
            GenderEntity entitySaved = this.genderRepository.save(entity);
            GenderDTO result = this.genderMapper.genderEntity2DTO(entitySaved);
            return result;
        }else{
            throw new NotFoundException("The Gender to update with id "+id + "does not exist");
        }
    }
    @Transactional
    @Override
    public void delete(String id) {
        if(this.genderRepository.existsById(id)){
            this.genderRepository.deleteById(id);
        }else {
            throw new NotFoundException("The gender to delete with id "+id+" does not exist");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenderDTO> getAll() {
        List<GenderEntity>entities = this.genderRepository.findAll();
        List<GenderDTO>dtos = this.genderMapper.genderEntityList2DTOList(entities);

        return dtos;
    }
    @Transactional(readOnly = true)
    @Override
    public GenderDTO getDetailsById(String id) {
        Optional<GenderEntity> entity = this.genderRepository.findById(id);
        if(!entity.isPresent()){
            throw new NotFoundException("The gender with id "+id+" wasn't found");
        }else {
            GenderDTO dto = this.genderMapper.genderEntity2DTO(entity.get());
            return dto;
        }
    }
    @Transactional(readOnly = true)
    @Override
    public GenderEntity getEntityById(String id) {
        Optional<GenderEntity> entity= this.genderRepository.findById(id);
        if(!entity.isPresent()){
            throw new NotFoundException("The gender with id "+id+" does not exist");
        }
        return entity.get();
    }
    @Transactional(readOnly = true)
    @Override
    public Boolean existById(String id) {
        return this.genderRepository.existsById(id);
    }

    @Override
    public void validateGender(GenderDTO genderDTO, GenderEntity genderEntity) {
        if(this.genderRepository.existsByName(genderDTO.getName()) && (genderEntity == null || !genderEntity.getName().equalsIgnoreCase(genderDTO.getName()))){
            throw new DuplicateValueException("There is already a gender with the name '"+genderDTO.getName()+"'");
        }

    }
}
