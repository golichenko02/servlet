package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.jpa.repository.CommonJpaRepository;
import org.hillel.persistence.jpa.repository.JourneyJpaRepository;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.persistence.jpa.repository.specification.JourneySpecification;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service(value = "transactionalJourneyService")
public class TransactionalJourneyService extends CommonService<JourneyEntity, Long> {

    @Autowired
    JourneyJpaRepository journeyRepository;

    public TransactionalJourneyService(CommonJpaRepository<JourneyEntity, Long> repository) {
        super(repository);
    }

    @Override
    public Collection<JourneyEntity> findAllWithSpecification(PaginationInfo paginationInfo) {
        return journeyRepository.findAll(getSpecification(paginationInfo.getSpecificationFilter(), paginationInfo.getValue()),
                paginationInfo.createPageRequest()).getContent();
    }

    @Override
    public Specification<JourneyEntity> getSpecification(ISpecification specification, String filterValue) {
        return JourneySpecification.valueOf(specification.toString()).getSpecification(filterValue);
    }

    @Transactional
    public Optional<JourneyEntity> findByIdWithDependencies(Long id, boolean withDependencies) {
        final Optional<JourneyEntity> byId = journeyRepository.findById(id);
        if (withDependencies && byId.isPresent()) {
            byId.get().getVehicle().getName();
            byId.get().getStops().size();
        }
        return byId;
    }

}
