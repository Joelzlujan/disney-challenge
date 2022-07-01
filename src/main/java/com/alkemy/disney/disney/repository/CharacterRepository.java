package com.alkemy.disney.disney.repository;

import com.alkemy.disney.disney.entity.CharacterEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity,String >, JpaSpecificationExecutor<CharacterEntity> {
    CharacterEntity findByName(String name);

    Boolean existsByName(String name);
    @Override
    List<CharacterEntity>findAll(Specification <CharacterEntity> spec);//hay q hacer q haga el override del findAll
}
