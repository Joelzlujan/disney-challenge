package com.alkemy.disney.disney.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@Table(name = "gender")
@Getter
@Setter
public class GenderEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2" )
    private String id;
    
    private String name;
    
    private String imagen;
   //encontre info en los apuntes q enviaste sobre que no hacia falta declarar los cascade en las entidades que no son dueñas de la relacion o sea 
   //solo declaro entonces en la entidad movie los cascade
    @ManyToMany(mappedBy = "genders", fetch = FetchType.LAZY)
    private List <MovieEntity> movies = new ArrayList<>();
    
    private Boolean softDelete = Boolean.FALSE;
    
}
