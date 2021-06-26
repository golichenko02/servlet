package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.CommonInfo_;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.StopEntity_;
import org.springframework.data.jpa.domain.Specification;

public enum StopSpecification implements ISpecification<StopEntity> {

    BY_ID {
        @Override
        public Specification<StopEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(StopEntity_.ID), criteriaBuilder.literal(Long.valueOf(value)));
        }
    },

    BY_NAME {
        @Override
        public Specification getSpecification(String value) {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get(StopEntity_.COMMON_INFO).get(CommonInfo_.NAME), criteriaBuilder.literal(value));
        }
    },

    BY_ONLY_ACTIVE {
        @Override
        public Specification<StopEntity> getSpecification() {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.isTrue(root.get(StopEntity_.ACTIVE));
        }
    }
}
