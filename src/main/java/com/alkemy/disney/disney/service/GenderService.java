package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.GenderDTO;
import com.alkemy.disney.disney.entity.GenderEntity;

import java.util.List;

public interface GenderService {
    List<GenderDTO> getAll();

    public GenderDTO getDetailsById(String id);

    public GenderDTO save(GenderDTO genderDTO);

    public GenderDTO update(String id,GenderDTO genderDTO);

    public void delete(String id);

    public GenderEntity getEntityById(String id);

    public Boolean existById(String id);

    public void validate(GenderDTO genderDTO, GenderEntity genderEntity);
}
