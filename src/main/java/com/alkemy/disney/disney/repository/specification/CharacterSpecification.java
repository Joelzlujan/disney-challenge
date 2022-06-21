package com.alkemy.disney.disney.repository.specification;

import com.alkemy.disney.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Component
public class CharacterSpecification {

    public Specification<CharacterEntity> getByFilters(CharacterFiltersDTO filterDTO){

        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();

            if(StringUtils.hasLength(filterDTO.getName())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filterDTO.getName().toLowerCase() + "%"
                        )
                );
            }
            if(filterDTO.getAge() != null && StringUtils.hasLength(filterDTO.getAge().toString())){
                predicates.add(
                    criteriaBuilder.equal(
                            root.get("age"),
                            filterDTO.getAge())
                    );
                }
            if(!CollectionUtils.isEmpty(filterDTO.getIdMovies())){
                Join<MovieEntity,CharacterEntity> join = root.join("movies", JoinType.INNER);
                Expression<String>moviesId = join.get("id");
                predicates.add(moviesId.in(filterDTO.getIdMovies()));
            }
            //remove duplicates
            query.distinct(true);
            //Order
            String orderByField="name";
            query.orderBy(
                    filterDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)):
                            criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
