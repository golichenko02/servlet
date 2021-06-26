package org.hillel.persistence.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class VehicleRepository extends CommonRepository<VehicleEntity, Long> {
    protected VehicleRepository() {
        super(VehicleEntity.class);
    }

    @Override
    public void remove(VehicleEntity entity) {
        entity = findById(entity.getId()).get();
        entity.removeAllJourneyAndSeats();
        super.remove(entity);
    }

    @Override
    public void removeById(Long id) {
        findById(id).get().removeAllJourneyAndSeats();
        super.removeById(id);
    }

    // Cписок транспортных средств с наибольшим количеством свободных мест

    public Collection<VehicleEntity> findVehicleWithMaxFreeSeats() {
        return entityManager.createNativeQuery("SELECT v.* " +
               "FROM vehicle v " +
               "JOIN vehicle_seat vs on v.id = vs.vehicle_id AND booked like 'no' " +
         "GROUP BY v.name, v.id " +
         "HAVING count(vs.booked) >= ALL (SELECT count(vs2.booked) " +
               "FROM vehicle v1 " +
               "JOIN vehicle_seat vs2 on v1.id = vs2.vehicle_id AND booked like 'no' " +
        "GROUP BY v1.name)", VehicleEntity.class).getResultList();
    }

    // Cписок транспортных средств с наименьшим количеством свободных мест
    public Collection<VehicleEntity> findVehicleWithMinFreeSeats() {
        return entityManager.createNativeQuery("SELECT v.* " +
               "FROM vehicle v " +
               "JOIN vehicle_seat vs on v.id = vs.vehicle_id AND booked like 'no' " +
         "GROUP BY v.name, v.id " +
         "HAVING count(vs.booked) <= ALL (SELECT count(vs2.booked) " +
               "FROM vehicle v1 " +
               "JOIN vehicle_seat vs2 on v1.id = vs2.vehicle_id AND booked like 'no' " +
        "GROUP BY v1.name)", VehicleEntity.class).getResultList();
    }

}
