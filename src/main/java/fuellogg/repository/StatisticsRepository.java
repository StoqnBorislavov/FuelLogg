package fuellogg.repository;

import fuellogg.model.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistic, Long> {

//    @Query("SELECT s FROM Statistic s WHERE s.vehicle.id= 5 ORDER BY s.date DESC ")
    Optional<Statistic> findTopByVehicle_IdOrderByDateDesc(Long vehicleId);

    Optional<List<Statistic>> findAllByVehicle_IdOrderByDateDesc(Long vehicleId);
}
