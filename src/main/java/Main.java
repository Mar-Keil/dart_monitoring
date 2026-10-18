import game.Control;
import io.*;
import persistence.HibernateSetup;

public class Main {
    public void main(String[] args){
        try (var sessionFactory = HibernateSetup.createSessionFactory()) {
            IO.println("Hibernate wurde erfolgreich gestartet.");
            Control controller = new Control(new Printer(), new Reader(), new Scan());
        }
    }
}
