package fuellogg.repository;

import fuellogg.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<List<Vehicle>> findByOwner_Username(String username);

}
