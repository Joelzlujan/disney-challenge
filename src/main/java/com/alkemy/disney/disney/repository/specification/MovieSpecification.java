package com.alkemy.disney.disney.repository.specification;

import com.alkemy.disney.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.disney.dto.GenderDTO;
import com.alkemy.disney.disney.dto.MovieFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.GenderEntity;
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

@Component
public class MovieSpecification {
    public Specification<MovieEntity> getByFilters(MovieFiltersDTO filterDTO){

        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList();

            if(StringUtils.hasLength(filterDTO.getTitle())){
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filterDTO.getTitle().toLowerCase() + "%"
                        )
                );
            }


            if(!CollectionUtils.isEmpty(filterDTO.getIdGenders())){
                Join<MovieEntity, GenderEntity> join = root.join("gender", JoinType.INNER);
                Expression<String> genderId = join.get("id");
                predicates.add(genderId.in(filterDTO.getIdGenders()));
            }
            //remove duplicates
            query.distinct(true);
            //Order
            String orderByField="title";
            query.orderBy(
                    filterDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)):
                            criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
