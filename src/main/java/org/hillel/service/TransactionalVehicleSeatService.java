package org.hillel.service;

import org.hillel.persistence.entity.VehicleSeatEntity;
import org.hillel.persistence.jpa.repository.CommonJpaRepository;
import org.hillel.persistence.jpa.repository.VehicleSeatJpaRepository;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSeatSpecification;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service(value = "transactionalVehicleSeatService")
public class TransactionalVehicleSeatService extends CommonService<VehicleSeatEntity, Long> {

    @Autowired
    VehicleSeatJpaRepository vehicleSeatRepository;


    public TransactionalVehicleSeatService(CommonJpaRepository<VehicleSeatEntity, Long> repository) {
        super(repository);
    }

    @Override
    public Collection<VehicleSeatEntity> findAllWithSpecification(PaginationInfo paginationInfo) {
        return vehicleSeatRepository.findAll(getSpecification(paginationInfo.getSpecificationFilter(), paginationInfo.getValue()),
                paginationInfo.createPageRequest()).getContent();
    }

    @Override
    public Specification<VehicleSeatEntity> getSpecification(ISpecification specification, String filterValue) {
        return VehicleSeatSpecification.valueOf(specification.toString()).getSpecification(filterValue);
    }
}
