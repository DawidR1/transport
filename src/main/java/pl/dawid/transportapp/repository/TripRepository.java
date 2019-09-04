package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawid.transportapp.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {

    //FIXME
//    @Query("select trip.id,trip.destination,trip.dateStart,trip.dateFinish,trip.placeFinish,trip.status from Trip trip where trip.id = :id")
//    public Optional<Trip> getNarrowTrip(Long id);
//
//    @Query("select trip.id,trip.destination,trip.dateStart,trip.dateFinish,trip.placeFinish,trip.status from Trip trip")
//    public List<Trip> getAllNarrowTrip();
}
