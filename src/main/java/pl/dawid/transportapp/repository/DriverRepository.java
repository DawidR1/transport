package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dawid.transportapp.model.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByPesel(String pesel);

    @Query("select driver.id from Driver driver")
    List<Long> findAllId();
}
