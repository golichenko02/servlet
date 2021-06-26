package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.JourneyEntity_;
import org.springframework.data.jpa.domain.Specification;

public enum JourneySpecification implements ISpecification<JourneyEntity> {

    BY_ID {
        @Override
        public Specification<JourneyEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.ID), criteriaBuilder.literal(Long.valueOf(value)));
        }
    },

    BY_STATION_FROM {
        @Override
        public Specification<JourneyEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.STATION_FROM), criteriaBuilder.literal(value));
        }
    },

    BY_STATION_TO {
        @Override
        public Specification<JourneyEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.STATION_TO), criteriaBuilder.literal(value));
        }
    },

    BY_ONLY_ACTIVE {
        @Override
        public Specification<JourneyEntity> getSpecification() {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.isTrue(root.get(JourneyEntity_.ACTIVE));
        }
    }
}
