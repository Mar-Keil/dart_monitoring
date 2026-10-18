package persistence;

import model.Player;
import org.hibernate.SessionFactory;

import java.util.List;

public class PlayerRepository {

    private final SessionFactory sessionFactory;

    public PlayerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Player player) {
        sessionFactory.inTransaction(session -> {
            session.persist(player);
        });
    }

    public List<Player> findAll() {
        return sessionFactory.fromTransaction(session -> {
            return session.createSelectionQuery(
                    "from Player order by lastname, firstname, id",
                    Player.class
            ).getResultList();
        });
    }
}
