package pl.dawid.transportapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.model.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("select trip from Trip trip left join fetch trip.loadingPlaces where trip.driver = :driver and trip.dateStart between :startDate and  :endDate")
    List<Trip> findAllByDriverAndDateStartBetween(Driver driver, LocalDate startDate, LocalDate endDate);

    @Query("select trip from Trip trip left join fetch trip.loadingPlaces where trip.id = :id")
    Optional<Trip> findByIdWithLoadingPlaces(Long id);

//    @Query("select trip from Trip trip left join fetch trip.loadingPlaces")
//    List<Trip> findWithLoadingPlaces();

    @Query(value = "select trip from Trip trip left join fetch trip.loadingPlaces order by :#{pageable}",
            countQuery = "select count(trip.id) from Trip trip")
    Page<Trip> findWithLoadingPlaces(Pageable pageable);
}
