package dart_monitoring.config;

import dart_monitoring.persistence.HibernateSetup;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ApplicationConfiguration {

    @Bean(destroyMethod = "close")
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean(destroyMethod = "close")
    public SessionFactory sessionFactory() {
        return HibernateSetup.createSessionFactory();
    }
}
