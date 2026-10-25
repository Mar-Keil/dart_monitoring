package dart_monitoring.persistence;

import dart_monitoring.model.Game;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

@NullMarked
public interface GameRepository extends JpaRepository<Game, Integer> {

    default Game saveWithUpdatedAt(Game game) {
        game.setUpdatedAt(Instant.now());
        return save(game);
    }
}
