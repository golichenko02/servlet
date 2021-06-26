package org.hillel.persistence.repository;

import org.hillel.persistence.entity.VehicleSeatEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleSeatRepository extends CommonRepository<VehicleSeatEntity, Long> {
    protected VehicleSeatRepository() {
        super(VehicleSeatEntity.class);
    }
}
