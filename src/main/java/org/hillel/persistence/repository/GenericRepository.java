package org.hillel.persistence.repository;

import org.hillel.service.query_info.PaginationInfo;

import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<E, ID> {

    E createOrUpdate(E entity);

    Optional<E> findById(ID id);

    void removeById(ID id);

    void remove(E entity);

    Collection<E> findByIds(ID... ids);

    Collection<E> findAllByName(String name);

    Collection<E> findAll(PaginationInfo paginationInfo);

    Collection<E> findAllAsNative(PaginationInfo paginationInfo);

    Collection<E> findAllAsNamed(PaginationInfo paginationInfo);

    Collection<E> findAllAsCriteria(PaginationInfo paginationInfo);

    Collection<E> findAllAsStoredProcedure();

}
