package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface VehicleJpaRepository extends CommonJpaRepository<VehicleEntity, Long>,
        JpaSpecificationExecutor<VehicleEntity> {

    @Query(value = "select v.* from vehicle v where  v.id between :id_from and :id_to and v.name = :name",
            countQuery = "select count(v.id) from vehicle v", nativeQuery = true)
    Page<VehicleEntity> findByConditions(@Param("name") String name,
                                         @Param("id_from") Long idFrom,
                                         @Param("id_to") Long idTo,
                                         Pageable page);

    List<SimpleVehicleDto> findAllByActiveIsTrue();

    @Query(value = "SELECT v.* FROM vehicle v " +
            "JOIN vehicle_seat vs on v.id = vs.vehicle_id AND booked like 'no' " +
            "GROUP BY v.name, v.id " +
            "HAVING count(vs.booked) >= ALL (SELECT count(vs2.booked) " +
            "FROM vehicle v1 " +
            "JOIN vehicle_seat vs2 on v1.id = vs2.vehicle_id AND booked like 'no' " +
            "GROUP BY v1.name)", nativeQuery = true)
    Collection<VehicleEntity> findVehicleWithMaxFreeSeats();

    @Query(value = "SELECT v.* FROM vehicle v " +
            "JOIN vehicle_seat vs on v.id = vs.vehicle_id AND booked like 'no' " +
            "GROUP BY v.name, v.id " +
            "HAVING count(vs.booked) <= ALL (SELECT count(vs2.booked) " +
            "FROM vehicle v1 " +
            "JOIN vehicle_seat vs2 on v1.id = vs2.vehicle_id AND booked like 'no' " +
            "GROUP BY v1.name)", nativeQuery = true)
    Collection<VehicleEntity> findVehicleWithMinFreeSeats();
}
