package fuellogg.repository;

import fuellogg.model.entity.StatisticFueling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsFuelingRepository extends JpaRepository<StatisticFueling, Long> {

//    @Query("SELECT s FROM Statistic s WHERE s.vehicle.id= 5 ORDER BY s.date DESC ")
    Optional<StatisticFueling> findTopByVehicle_IdOrderByCreatedDesc (Long vehicleId);

    Optional<List<StatisticFueling>> findAllByVehicle_IdOrderByCreatedDesc(Long vehicleId);

    Optional<StatisticFueling> findTopByVehicle_IdOrderByCreatedAsc(Long vehicleId);
}
