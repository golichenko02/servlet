package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.VehicleSeatEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleSeatJpaRepository extends CommonJpaRepository<VehicleSeatEntity, Long>,
        JpaSpecificationExecutor<VehicleSeatEntity> {
}
