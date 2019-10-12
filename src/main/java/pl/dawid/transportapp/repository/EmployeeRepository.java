package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dawid.transportapp.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
