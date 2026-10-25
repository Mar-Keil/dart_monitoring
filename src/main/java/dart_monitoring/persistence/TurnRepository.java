package dart_monitoring.persistence;

import dart_monitoring.model.Turn;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TurnRepository {

    private final SessionFactory sessionFactory;

    public TurnRepository(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    public void save(Turn turn) {
        sessionFactory.inTransaction(session -> {
            session.persist(turn);
        });
    }
}
