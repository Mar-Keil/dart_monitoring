import game.GameControl;
import game.PlayerSelection;
import console.ConsoleInput;
import console.ConsoleOutput;

import persistence.*;

import java.util.Scanner;

public class Main {
    public void main(String[] args){

        try (Scanner scanner = new Scanner(System.in);
             var sessionFactory = HibernateSetup.createSessionFactory()) {
            ConsoleInput input = new ConsoleInput(scanner);
            ConsoleOutput.section("Hibernate started successfully.");

            PlayerRepository playerRepository = new PlayerRepository(sessionFactory);
            GameRepository gameRepository = new GameRepository(sessionFactory);
            TurnRepository turnRepository = new TurnRepository(sessionFactory);

            PlayerSelection playerSelection = new PlayerSelection(playerRepository, input);
            GameControl control = new GameControl(gameRepository, turnRepository, input);

            control.gameLoop(playerSelection.getPlayers());
        }
    }
}
