/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alkemy.disney.disney.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

/**
 *
 * @author Joel
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "movie")
@SQLDelete(sql = "UPDATE movie SET deleted= true WHERE id=?") //esta anotacion sirve para usar el soft delete
@Where(clause = "deleted = false")//de esta manera queda diferenciado aquellos que fueron borrados los q no.
public class MovieEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, length = 100)
    private String title;
    
    //@Transient cuando creo una película, ignora totalmente lo q le pongas a esta notación, la ignora cuando crea la tabla
    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;

    @Nullable
    private Float rating;

    //el fetch en el manytomany se inicializa en lazy
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_character",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<CharacterEntity> characters = new ArrayList<>();

    //recordar que el fetch en ManyToOne se inicializa en eager. asi q no es necesario declararlo.
    //es unidireccional
    @ManyToOne()
    @JoinColumn(name = "gender_id") // referencedColumnName = con este haria la relacion en la tabla
    private GenderEntity gender;

    @Column(name = "gender_id")
    private String genderId; //to create or update

    // fetch -> MAKES THE INITIALIZATION AN EARLY TYPE, EACH TIME IT ASKS FOR AN
    // MOVIE, IT'S GOING TO COME WITH ALL GENDERS)
    //Attribute to soft delete
    private boolean deleted = Boolean.FALSE;

    //ADD CHARACTER
    public void addCharacter(CharacterEntity character) {
        this.characters.add(character);
    }

    //REMOVE CHARACTER
    public void removeCharacter(CharacterEntity character) {
        this.characters.remove(character);
    }
}
