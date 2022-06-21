package com.alkemy.disney.disney.repository;

import com.alkemy.disney.disney.entity.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<GenderEntity,String> {

    Boolean existsByName(String name);
}
