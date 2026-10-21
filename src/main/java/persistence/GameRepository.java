package persistence;

import model.Game;
import org.hibernate.SessionFactory;

public class GameRepository {

    private final SessionFactory sessionFactory;

    public GameRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Game game) {
        sessionFactory.inTransaction(session -> {
            if (game.getId() == null) session.persist(game);
            else session.merge(game);
        });
    }
}
