package com.alkemy.disney.disney.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "characters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "UPDATE characters SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class CharacterEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;

    private String image;

    private String name;

    private Integer age;

    private Double weight;

    private String history;

//RELATION BETWEEN CHARACTER -> MOVIES
    @ManyToMany(mappedBy = "characters", fetch = FetchType.LAZY, cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH})
    private List<MovieEntity> movies = new ArrayList<>();

    private boolean deleted = Boolean.FALSE;
}
