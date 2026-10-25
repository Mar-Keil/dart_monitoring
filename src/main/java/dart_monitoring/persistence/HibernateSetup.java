package dart_monitoring.persistence;

import dart_monitoring.model.Game;
import dart_monitoring.model.Player;
import dart_monitoring.model.Turn;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSetup {

    public static SessionFactory createSessionFactory() {
        var registry = new StandardServiceRegistryBuilder()
                .build();

        try {
            return new MetadataSources(registry)
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(Game.class)
                    .addAnnotatedClass(Turn.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (RuntimeException e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }
}
