package dart_monitoring.persistence;

import dart_monitoring.model.Player;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

@NullMarked
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
