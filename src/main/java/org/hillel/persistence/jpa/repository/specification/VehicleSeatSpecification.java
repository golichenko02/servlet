package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.VehicleSeatEntity;
import org.hillel.persistence.entity.VehicleSeatEntity_;
import org.springframework.data.jpa.domain.Specification;

public enum VehicleSeatSpecification implements ISpecification<VehicleSeatEntity> {

    BY_ID {
        @Override
        public Specification<VehicleSeatEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(VehicleSeatEntity_.ID), criteriaBuilder.literal(value));
        }
    },

    BY_ONLY_ACTIVE {
        @Override
        public Specification<VehicleSeatEntity> getSpecification() {
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.isTrue(root.get(VehicleSeatEntity_.ACTIVE));
        }
    },

    BY_ONLY_BOOKED {
        @Override
        public Specification<VehicleSeatEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(VehicleSeatEntity_.isBooked));
        }
    },

    BY_SEAT_NUMBER {
        @Override
        public Specification<VehicleSeatEntity> getSpecification(String value) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(VehicleSeatEntity_.seatNumber), criteriaBuilder.literal(value));
        }
    }
}
