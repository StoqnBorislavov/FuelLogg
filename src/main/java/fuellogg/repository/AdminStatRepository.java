package fuellogg.repository;

import fuellogg.model.entity.AdminStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface AdminStatRepository extends JpaRepository<AdminStat, Long> {

    Optional<AdminStat> findTopByCreatedAfterOrderByLogTimeDesc(Instant timeBeforeOneDay);

    void deleteAllByCreatedBetween(Instant timeBeforeOneDay, Instant currentTime);
}
