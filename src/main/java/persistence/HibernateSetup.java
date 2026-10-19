package persistence;

import model.Game;
import model.Player;
import model.Turn;
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
