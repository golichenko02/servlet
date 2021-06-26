package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleSeatEntity;
import org.hillel.persistence.jpa.repository.SimpleVehicleDto;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class TicketClient {

    @Autowired
    @Qualifier("transactionalJourneyService")
    private GenericService transactionalJourneyService;

    @Autowired
    @Qualifier("transactionalStopService")
    private GenericService transactionalStopService;

    @Autowired
    @Qualifier("transactionalVehicleService")
    private GenericService transactionalVehicleService;

    @Autowired
    @Qualifier("transactionalVehicleSeatService")
    private GenericService transactionalVehicleSeatService;

    public JourneyEntity createOrUpdateJourney(final JourneyEntity journeyEntity) {
        return (JourneyEntity) transactionalJourneyService.createOrUpdate(journeyEntity);
    }

    public StopEntity createOrUpdateStop(final StopEntity stopEntity) {
        return (StopEntity) transactionalStopService.createOrUpdate(stopEntity);
    }

    public VehicleEntity createOrUpdateVehicle(final VehicleEntity vehicleEntity) {
        return (VehicleEntity) transactionalVehicleService.createOrUpdate(vehicleEntity);
    }

    public VehicleSeatEntity createOrUpdateSeat(final VehicleSeatEntity vehicleSeatEntity) {
        return (VehicleSeatEntity) transactionalVehicleSeatService.createOrUpdate(vehicleSeatEntity);
    }

    public void removeJourney(JourneyEntity journeyEntity) {
        transactionalJourneyService.remove(journeyEntity);
    }

    public void removeJourneyById(Long id) {
        transactionalJourneyService.removeById(id);
    }


    public void removeVehicle(VehicleEntity vehicleEntity) {
        transactionalVehicleService.remove(vehicleEntity);
    }

    public void removeVehicleById(Long id) {
        transactionalVehicleService.removeById(id);
    }

    public void removeStop(StopEntity stopEntity) {
        transactionalStopService.remove(stopEntity);
    }

    public void removeStopById(Long id) {
        transactionalStopService.removeById(id);
    }

    public Collection<VehicleEntity> findVehiclesByIds(Long... ids) {
        return transactionalVehicleService.findByIds(ids);
    }

    public Optional<VehicleEntity> findVehicleById(Long id) {
        return transactionalVehicleService.findById(id);
    }

    public Optional<JourneyEntity> findJourneyById(Long id) {
        return transactionalJourneyService.findById(id);
    }
    public Optional<JourneyEntity> findJourneyById(Long id, boolean withDependencies){
        return  transactionalJourneyService.findByIdWithDependencies(id, withDependencies);
    }

    public Optional<StopEntity> findStopById(Long id, boolean withDependencies){
        return  transactionalStopService.findByIdWithDependencies(id, withDependencies);
    }

    public Collection<VehicleEntity> findAllVehicles(PaginationInfo paginationInfo) {
        return transactionalVehicleService.findAll(paginationInfo);
    }

    public Collection<JourneyEntity> findAllJourneys(PaginationInfo paginationInfo) {
        return transactionalJourneyService.findAll(paginationInfo);
    }

    public Collection<StopEntity> findAllStops(PaginationInfo paginationInfo) {
        return transactionalStopService.findAll(paginationInfo);
    }

    public Collection<VehicleSeatEntity> findAllSeats(PaginationInfo paginationInfo) {
        return transactionalVehicleSeatService.findAll(paginationInfo);
    }

    public Collection<StopEntity> findAllStopsWithSpecification(PaginationInfo paginationInfo) {
        return transactionalStopService.findAllWithSpecification(paginationInfo);
    }

    public Collection<JourneyEntity> findAllJourneysWithSpecification(PaginationInfo paginationInfo) {
        return transactionalJourneyService.findAllWithSpecification(paginationInfo);
    }

    public Collection<VehicleSeatEntity> findAllSeatsWithSpecification(PaginationInfo paginationInfo) {
        return transactionalVehicleSeatService.findAllWithSpecification(paginationInfo);
    }

    public Collection<VehicleEntity> findAllVehiclesWithSpecification(PaginationInfo paginationInfo) {
        return transactionalVehicleService.findAllWithSpecification(paginationInfo);
    }

    public Collection<VehicleEntity> findVehicleWithMaxFreeSeats() {
        return transactionalVehicleService.findWithMaxFreeSeats();
    }

    public Collection<VehicleEntity> findVehicleWithMinFreeSeats() {
        return transactionalVehicleService.findWithMinFreeSeats();
    }

    public void disableById(Long id) {
        transactionalVehicleService.disableById(id);
    }


    public List<SimpleVehicleDto> listAllSimpleVehicles() {
        return transactionalVehicleService.listAllSimpleVehicles();
    }


}
