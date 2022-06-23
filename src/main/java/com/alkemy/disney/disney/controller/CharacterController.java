package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("characters")
public class CharacterController {

    private CharacterService characterService;
    private MovieService movieService;

    @Autowired
    public CharacterController(CharacterService characterService,MovieService movieService){
        this.characterService = characterService;
        this.movieService = movieService;
    }
    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>>getAll(){
        List<CharacterBasicDTO>characters = this.characterService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(characters);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getDetailsById(@PathVariable String id){
        CharacterDTO result = this.characterService.getDetailsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping
    public ResponseEntity<CharacterDTO>save(@Valid @RequestBody CharacterDTO characterDTO){
        CharacterDTO result = this.characterService.save(characterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO>modify(@PathVariable String id,@Valid @RequestBody CharacterDTO characterDTO){
        CharacterDTO result = this.characterService.update(id,characterDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/{idCharacter}/movie/{idMovie}")
    public ResponseEntity<Void> addMovie(@PathVariable String idCharacter, @PathVariable String idMovie){
        this.movieService.addCharacter(idMovie,idCharacter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /* Este delete no es necesario como endpoint
    @DeleteMapping("/{idCharacter}/movie/{idMovie}")
    public ResponseEntity<Void> removeMovie(@PathVariable String idCharacter, @PathVariable String idMovie){
        this.movieService.removeCharacter(idMovie,idCharacter);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

     */
    @GetMapping//("/filter") no es necesario el filter en el api rest
    public ResponseEntity<List<CharacterDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Set<String> movies,
            @RequestParam(required = false, defaultValue = "ASC")String order
    ){
        List<CharacterDTO> result = this.characterService.getDetailsByFilters(name,age,movies,order);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
