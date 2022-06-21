package com.alkemy.disney.disney.controller;


import com.alkemy.disney.disney.dto.GenderDTO;
import com.alkemy.disney.disney.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("genres")
public class GenderController {

    private GenderService genderService;

    @Autowired
    public GenderController(GenderService genderService){
        this.genderService = genderService;
    }
    @GetMapping
    public ResponseEntity<List<GenderDTO>> getAll(){
        List<GenderDTO>genders = this.genderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(genders);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GenderDTO> getDetailsById (@PathVariable String id){
        GenderDTO result = this.genderService.getDetailsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @PostMapping
    public ResponseEntity<GenderDTO> save(@Valid @RequestBody GenderDTO genderDTO){
        GenderDTO result = this.genderService.save(genderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenderDTO> modify(@PathVariable String id,@Valid @RequestBody GenderDTO dto){
        GenderDTO result = this.genderService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.genderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
