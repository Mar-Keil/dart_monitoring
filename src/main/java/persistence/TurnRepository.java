package persistence;

import model.Turn;
import org.hibernate.SessionFactory;

public class TurnRepository {

    private final SessionFactory sessionFactory;

    public TurnRepository(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    public void save(Turn turn) {
        sessionFactory.inTransaction(session -> {
            session.persist(turn);
        });
    }
}
