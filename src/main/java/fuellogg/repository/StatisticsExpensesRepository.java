package fuellogg.repository;

import fuellogg.model.entity.StatisticExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsExpensesRepository extends JpaRepository<StatisticExpenses, Long> {

    Optional<List<StatisticExpenses>> findAllByVehicle_IdOrderByDateDesc(Long vehicleId);
}
