package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieService movieService;


    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getDetailsById(@PathVariable String id){
        MovieDTO result = this.movieService.getDetailsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping //ACA DEBERIA ACHICAR EL DTO
    public ResponseEntity<MovieDTO> save(@Valid @RequestBody MovieDTO movieDTO){
        MovieDTO result = this.movieService.save(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> modify(@PathVariable String id, @Valid @RequestBody MovieDTO movieDTO){ //sin el @Valid no sirven los @Null en los dto
        MovieDTO result = this.movieService.update(id,movieDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<Void> addCharacter(@PathVariable String idCharacter, @PathVariable String idMovie){
        this.movieService.addCharacter(idMovie,idCharacter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{idMovie}/character/{idCharacter}")
    public ResponseEntity<Void> removeCharacter(@PathVariable String idCharacter, @PathVariable String idMovie){
        this.movieService.removeCharacter(idMovie,idCharacter);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping //no es necesario usar el filter, ya que saque el getAll
    public ResponseEntity<List<MovieDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Set<String> genders,
            @RequestParam(required = false, defaultValue = "ASC") String order){
        List<MovieDTO> result = this.movieService.getDetailsByFilters(name,genders,order);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
