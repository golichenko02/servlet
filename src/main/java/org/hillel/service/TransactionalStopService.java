package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.CommonJpaRepository;
import org.hillel.persistence.jpa.repository.StopJpaRepository;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.persistence.jpa.repository.specification.StopSpecification;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service("transactionalStopService")
public class TransactionalStopService extends CommonService<StopEntity, Long> {

    @Autowired
    private StopJpaRepository stopRepository;

    public TransactionalStopService(CommonJpaRepository<StopEntity, Long> repository) {
        super(repository);
    }


    @Override
    public Collection<StopEntity> findAllWithSpecification(PaginationInfo paginationInfo) {
        return stopRepository.findAll(getSpecification(paginationInfo.getSpecificationFilter(), paginationInfo.getValue()),
                paginationInfo.createPageRequest()).getContent();
    }

//    @Override
//    public Collection<StopEntity> findAllByName(String name) {
//        return stopRepository.findAll(StopSpecification.BY_NAME.getSpecification(name));
//    }

    @Override
    public Specification<StopEntity> getSpecification(ISpecification specification, String filterValue) {
        return StopSpecification.valueOf(specification.toString()).getSpecification(filterValue);
    }

    @Transactional
    public Optional<StopEntity> findByIdWithDependencies(Long id, boolean withDependencies) {
        final Optional<StopEntity> byId = stopRepository.findById(id);
        if (withDependencies && byId.isPresent()) {
            byId.get().getAdditionalInfo().getBuildDate();
            byId.get().getAdditionalInfo().getCity();
            byId.get().getAdditionalInfo().getLatitude();
            byId.get().getAdditionalInfo().getLongitude();
        }
        return byId;
    }
}
