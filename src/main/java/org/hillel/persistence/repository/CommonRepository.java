package org.hillel.persistence.repository;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.persistence.entity.AbstractModifyEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


public abstract class CommonRepository<E extends AbstractModifyEntity<ID>, ID extends Serializable> implements GenericRepository<E, ID> {

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<E> entityClass;

    protected CommonRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public E createOrUpdate(E entity) {
        Assert.notNull(entity, "entity must be set");
        if (Objects.isNull(entity.getId()))
            entityManager.persist(entity);
        else
            return entityManager.merge(entity);
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        if (Objects.isNull(id)) return Optional.empty();
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @SneakyThrows
    @Override
    public void removeById(ID id) {
        entityManager.remove(entityManager.getReference(entityClass, id));
    }

    @Override
    public void remove(E entity) {
        if (entityManager.contains(entity))
            entityManager.remove(entity);
        else
            removeById(entity.getId());

    }

    @Override
    public Collection<E> findByIds(ID... ids) {
        return entityManager.unwrap(Session.class).byMultipleIds(entityClass).multiLoad(ids);
    }

    @Override
    public Collection<E> findAllByName(String name) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        final Root<E> from = query.from(entityClass);
        final Predicate byName = criteriaBuilder.equal(from.get(VehicleEntity_.NAME),
                criteriaBuilder.parameter(String.class, "nameParam"));
        return entityManager.createQuery(query.select(from).where(byName))
                .setParameter("nameParam", name).getResultList();
    }


    @Override
    public Collection<E> findAll(PaginationInfo paginationInfo) {
        return entityManager.createQuery("select e from " + entityClass.getSimpleName() + " e " + paginationInfo.getOrderBy(), entityClass)
                .setFirstResult(paginationInfo.getFirstResult())
                .setMaxResults(paginationInfo.getPageSize())
                .getResultList();
    }

    @Override
    public Collection<E> findAllAsNative(PaginationInfo paginationInfo) {
        return entityManager.createNativeQuery("select * from " + entityClass.getAnnotation(Table.class).name() +
                paginationInfo.getOrderBy(), entityClass)
                .setFirstResult(paginationInfo.getFirstResult())
                .setMaxResults(paginationInfo.getPageSize())
                .getResultList();
    }

    @Override
    public Collection<E> findAllAsNamed(PaginationInfo paginationInfo) {
        return entityManager
                .createNamedQuery(entityClass.getAnnotation(NamedQueries.class).value()[0].name(), entityClass)
                .setFirstResult(paginationInfo.getFirstResult())
                .setMaxResults(paginationInfo.getPageSize())
                .getResultList();
    }

    @Override
    public Collection<E> findAllAsCriteria(PaginationInfo paginationInfo) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        final Root<E> from = query.from(entityClass);
        final OrderImpl order = new OrderImpl(from.get(paginationInfo.getOrderField()), paginationInfo.isAsc());
        return entityManager.createQuery(query.select(from).orderBy(order))
                .setFirstResult(paginationInfo.getFirstResult())
                .setMaxResults(paginationInfo.getPageSize())
                .getResultList();
    }

    @Override
    public Collection<E> findAllAsStoredProcedure() {
        return entityManager.createStoredProcedureQuery("find_all", entityClass)
                .registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .setParameter(2, entityClass.getAnnotation(Table.class).name())
                .getResultList();
    }
}
