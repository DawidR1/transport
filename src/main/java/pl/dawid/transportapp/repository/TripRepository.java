package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dawid.transportapp.model.Employee;
import pl.dawid.transportapp.model.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findAllByDriverAndDateStartBetween(Employee employee, LocalDate startDate, LocalDate endDate);

    @Query("select trip from Trip trip left join fetch trip.loadingPlaces where trip.id = :id")
    Optional<Trip> findByIdWithLoadingPlaces(Long id);

    @Query("select trip from Trip trip left join fetch trip.loadingPlaces")
    List<Trip> findWithLoadingPlaces();
}
