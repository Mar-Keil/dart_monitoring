import game.GameControl;
import game.PlayerSelection;

import persistence.*;

import java.util.Scanner;

public class Main {
    public void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        try (var sessionFactory = HibernateSetup.createSessionFactory()) {
            IO.println("\nHibernate wurde erfolgreich gestartet.");

            PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
            GameRepository gameRepository = new GameRepository(sessionFactory);
            TurnRepository turnRepository = new TurnRepository(sessionFactory);

            PlayerSelection playerSelection = new PlayerSelection(playerRepository, scanner);
            GameControl control = new GameControl(gameRepository, turnRepository, scanner);

            control.startGame(playerSelection.getPlayers());
        }
    }
}
