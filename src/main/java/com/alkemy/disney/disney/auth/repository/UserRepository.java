package com.alkemy.disney.disney.auth.repository;

import com.alkemy.disney.disney.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    // Tengo el problema que despues de crear una query que devuelve un objeto, no puedo crear una q
    //quiero q me devuelva un booleano, me paso aca y me paso en Character Repository

    Boolean existsByUsername(String username);
}
