package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.jpa.repository.SimpleVehicleDto;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface GenericService<E, ID> {
    E createOrUpdate(final E entity);

    void remove(E vehicleEntity);

    void removeById(ID id);

    Collection<E> findByIds(ID... ids);

    default Collection<E> findAllByName(String name) {
        return Collections.emptyList();
    }

    Collection<E> findAll(PaginationInfo paginationInfo);

    Collection<E> findAllWithSpecification(PaginationInfo paginationInfo);

    Optional<E> findById(ID id);

    default Collection<E> findWithMaxFreeSeats() {
        return Collections.emptyList();
    }

    default Collection<E> findWithMinFreeSeats() {
        return Collections.emptyList();
    }

    default void disableById(Long id) {
    }

    default List<SimpleVehicleDto> listAllSimpleVehicles() {
        return Collections.emptyList();
    }

    Specification<E> getSpecification(ISpecification filterKey, String filterValue);

    Optional<E> findByIdWithDependencies(ID id, boolean withDependencies);
}

