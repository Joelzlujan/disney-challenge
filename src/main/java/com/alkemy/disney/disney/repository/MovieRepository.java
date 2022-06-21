package com.alkemy.disney.disney.repository;

import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,String>, JpaSpecificationExecutor {

    Boolean existsByTitle(String title);

    @Modifying
    @Query(value = "UPDATE movies SET genre_id = NULL WHERE genre_id =?;", nativeQuery = true)
    void removeAllGender(String idGender);
}
