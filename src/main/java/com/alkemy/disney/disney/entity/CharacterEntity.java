package com.alkemy.disney.disney.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Joel
 */
@Entity
@Table(name = "character")
@Getter
@Setter
public class CharacterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private Long id;

    private String image;

    private String name;

    private Integer age;

    private Integer weight;

    private String history;

    //RELATION BETWEEN CHARACTER -> MOVIES
    @ManyToMany(mappedBy = "characters", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List <MovieEntity> movies = new ArrayList<>();

    private Boolean softDelete = Boolean.FALSE;

}
