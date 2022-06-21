package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.GenderDTO;
import com.alkemy.disney.disney.entity.GenderEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenderMapper {
    // MAPPER TO PASS FROM DTO GENDER TO ENTITY GENDER
    public GenderEntity genderDTO2Entity (GenderDTO genderDTO){
        GenderEntity genderEntity = new GenderEntity();
        genderEntity.setImage(genderDTO.getImage());
        genderEntity.setName(genderDTO.getName());
        return genderEntity;
    }
    // MAPPER TO PASS FROM ENTITY GENDER TO DTO GENDER
    public GenderDTO genderEntity2DTO (GenderEntity genderEntity) {
        GenderDTO genderDTO = new GenderDTO();
        if (genderEntity != null) {
            genderDTO.setId(genderEntity.getId());
            genderDTO.setImage(genderEntity.getImage());
            genderDTO.setName(genderEntity.getName());
            return genderDTO;
        }
        return null;
    }
    // MAPPER FOR RETURN A LIST OF DTO GENDERS
        public List<GenderDTO> genderEntityList2DTOList(List<GenderEntity> entities) {
            List<GenderDTO> genderDTOList = new ArrayList();
            for (GenderEntity entity : entities) {
                genderDTOList.add(this.genderEntity2DTO(entity));
            }
            return genderDTOList;
        }
    public void genderEntityRefreshValues(GenderEntity genderEntity, GenderDTO genderDTO){
        genderEntity.setImage(genderDTO.getImage());
        genderEntity.setName(genderDTO.getName());
    }
}
