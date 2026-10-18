package persistence;

import model.Spieler;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSetup {

    public static SessionFactory createSessionFactory() {
        var registry = new StandardServiceRegistryBuilder()
                .build();

        try {
            return new MetadataSources(registry)
                    .addAnnotatedClass(Spieler.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (RuntimeException e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }
    }
}
