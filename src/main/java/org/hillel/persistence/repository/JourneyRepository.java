package org.hillel.persistence.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JourneyRepository extends CommonRepository<JourneyEntity, Long> {

    protected JourneyRepository() {
        super(JourneyEntity.class);
    }

    @Override
    public JourneyEntity createOrUpdate(JourneyEntity entity) {
//        VehicleEntity vehicleEntity = entity.getVehicle();
//        if (Objects.nonNull(vehicleEntity)) {
//            if (!entityManager.contains(vehicleEntity)) {
//                entity.addVehicle(entityManager.merge(vehicleEntity));
//            }
//        }
        return super.createOrUpdate(entity);
    }
}
