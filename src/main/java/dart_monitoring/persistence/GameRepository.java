package dart_monitoring.persistence;

import dart_monitoring.model.Game;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class GameRepository {

    private final SessionFactory sessionFactory;

    public GameRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Game game) {
        sessionFactory.inTransaction(session -> {
            game.setUpdatedAt(Instant.now());
            if (game.getId() == null) session.persist(game);
            else session.merge(game);
        });
    }
}
