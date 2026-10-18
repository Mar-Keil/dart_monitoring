import game.GameControl;
import game.PlayerSelection;

import persistence.HibernateSetup;
import persistence.PlayerRepository;

import java.util.Scanner;

public class Main {
    public void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        try (var sessionFactory = HibernateSetup.createSessionFactory()) {
            IO.println("\nHibernate wurde erfolgreich gestartet.");

            PlayerRepository playerRepository = new PlayerRepository(sessionFactory);

            PlayerSelection playerSelection = new PlayerSelection(playerRepository, scanner);
            GameControl control = new GameControl();

            control.startGame(playerSelection.getPlayers());
        }
    }
}
