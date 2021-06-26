package org.hillel.service;

import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.jpa.repository.CommonJpaRepository;
import org.hillel.persistence.jpa.repository.SimpleVehicleDto;
import org.hillel.persistence.jpa.repository.VehicleJpaRepository;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.List;

@Service(value = "transactionalVehicleService")
public class TransactionalVehicleService extends CommonService<VehicleEntity, Long> {

    @Autowired
    private VehicleJpaRepository vehicleJpaRepository;

//    @Autowired
//    private TransactionTemplate transactionTemplate;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

//    @PersistenceContext
//    EntityManagerFactory entityManagerFactory;

    public TransactionalVehicleService(CommonJpaRepository<VehicleEntity, Long> repository) {
        super(repository);
    }


    @Transactional
    @Override
    public VehicleEntity createOrUpdate(VehicleEntity entity) {
        return vehicleJpaRepository.save(entity);
//        // 1st way to manage transaction
//        return transactionTemplate.execute(transactionStatus -> vehicleRepository.createOrUpdate(entity));

        // 2nd way to manage transaction
//        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        final TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
//        vehicleRepository.createOrUpdate(entity);
//        platformTransactionManager.commit(transaction);
//        // if exception then
//        platformTransactionManager.rollback(transaction);
//        return entity;

        // one more way to manage transaction
//        one more way to manage transaction
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        final EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//        entityManager.persist(entity);
//        transaction.commit();
//        // if exception then
//        transaction.rollback();
//        return entity;
    }

    @Override
    @Transactional
    public void disableById(Long id) {
        vehicleJpaRepository.disableById(id);
    }

    @Override
    public Collection<VehicleEntity> findAllWithSpecification(PaginationInfo paginationInfo) {
        return vehicleJpaRepository.findAll(getSpecification(paginationInfo.getSpecificationFilter(), paginationInfo.getValue()),
                paginationInfo.createPageRequest()).getContent();
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Collection<VehicleEntity> findAllByName(String name) {
//        return vehicleJpaRepository.findAll(VehicleSpecification.ONLY_ACTIVE.BY_NAME.getSpecification(name));
//        VehicleEntity vehicleEntity = new VehicleEntity();
//        vehicleEntity.setMaxSeats(100);
//        vehicleEntity.setId(10l);
//        final Page<VehicleEntity> byConditions = vehicleJpaRepository
//                .findByConditions(name, 1L, 10L, PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, VehicleEntity_.ID)));
//
//        System.out.println(byConditions.getContent());
//
//
//        return vehicleJpaRepository.findByConditions(name, 1l, 10L, byConditions.nextPageable()).getContent();
//        return vehicleJpaRepository.findOnlyActive();
//        return vehicleJpaRepository.findAll(Example.of(new VehicleEntity().setCommonInfo(new CommonInfo().setName(name)).setMaxSeats(100)));
//        return vehicleJpaRepository.findAll(Example.of(new VehicleEntity().setName(name), ExampleMatcher.matching().withIgnorePaths(VehicleEntity_.MAX_SEATS)));
//        return vehicleJpaRepository
//                .findAll(VehicleSpecification
//                        .byNameAndExample(
//                                name, vehicleEntity));
//    }

    @Transactional(readOnly = true)
    @Override
    public List<SimpleVehicleDto> listAllSimpleVehicles() {
        return vehicleJpaRepository.findAllByActiveIsTrue();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<VehicleEntity> findWithMaxFreeSeats() {
        return vehicleJpaRepository.findVehicleWithMaxFreeSeats();
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<VehicleEntity> findWithMinFreeSeats() {
        return vehicleJpaRepository.findVehicleWithMinFreeSeats();
    }

    @Override
    public Specification<VehicleEntity> getSpecification(ISpecification specification, String filterValue) {
        return VehicleSpecification.valueOf(specification.toString()).getSpecification(filterValue);
    }
}
