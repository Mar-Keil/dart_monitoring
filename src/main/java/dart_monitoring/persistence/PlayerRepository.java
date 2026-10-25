package dart_monitoring.persistence;

import dart_monitoring.model.Player;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
