package dart_monitoring.persistence;

import dart_monitoring.model.Player;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NullMarked
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Override
    @Query("select player from Player player order by player.id")
    List<Player> findAll();
}
