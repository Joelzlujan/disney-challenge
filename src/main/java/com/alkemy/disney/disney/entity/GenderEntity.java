package com.alkemy.disney.disney.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Joel
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "gender")
@Getter
@Setter
@SQLDelete(sql = "UPDATE gender SET deleted = true WHERE id = ? ")
@Where(clause = "deleted = false")
public class GenderEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2" )
    private String id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String image;
   //encontre info en los apuntes q enviaste sobre que no hacia falta declarar los cascade en las entidades que no son dueñas de la relacion o sea 
   //solo declaro entonces en la entidad movie los cascade
    //no declaro la relacion xq es unidireccional
    
    private Boolean deleted = Boolean.FALSE;
    
}
