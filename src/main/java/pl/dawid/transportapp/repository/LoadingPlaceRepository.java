package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dawid.transportapp.model.LoadingPlace;

@Repository
public interface LoadingPlaceRepository extends JpaRepository<LoadingPlace, Long> {
}
