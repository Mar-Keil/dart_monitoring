package dart_monitoring;

import dart_monitoring.console.ConsoleOutput;
import dart_monitoring.game.GameControl;
import dart_monitoring.game.PlayerSelection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        try (var context = SpringApplication.run(Main.class, args)) {
            GameControl gameControl = context.getBean(GameControl.class);
            PlayerSelection playerSelection = context.getBean(PlayerSelection.class);

            ConsoleOutput.section("Hibernate started successfully.");
            gameControl.gameLoop(playerSelection.getPlayers());
        }
    }
}/ex
