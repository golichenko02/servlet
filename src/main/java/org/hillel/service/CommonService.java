package org.hillel.service;

import org.hillel.persistence.entity.AbstractModifyEntity;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public abstract class CommonService<E extends AbstractModifyEntity<ID>, ID extends Serializable> implements GenericService<E, ID> {

    private final PagingAndSortingRepository<E, ID> repository;

    public CommonService(PagingAndSortingRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<E> findAll(PaginationInfo paginationInfo) {
        return repository.findAll(paginationInfo.createPageRequest()).getContent();
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<E> findByIdWithDependencies(ID id, boolean withDependencies) {
        final Optional<E> byId = repository.findById(id);
        if (withDependencies && byId.isPresent()) {
            // your logic
        }
        return byId;
    }

    @Override
    public E createOrUpdate(E entity) {
        return repository.save(entity);
    }

    @Override
    public void remove(E vehicleEntity) {
        repository.delete(vehicleEntity);
    }

    @Override
    public void removeById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<E> findByIds(ID... ids) {
        return (Collection<E>) repository.findAllById(Arrays.asList(ids));
    }
}
