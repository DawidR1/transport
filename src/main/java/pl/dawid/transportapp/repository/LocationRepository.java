package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dawid.transportapp.model.Location;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
