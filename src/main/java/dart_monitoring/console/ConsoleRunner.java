package dart_monitoring.console;

import dart_monitoring.game.GameControl;
import dart_monitoring.game.PlayerSelection;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final GameControl gameControl;
    private final PlayerSelection playerSelection;

    public ConsoleRunner(GameControl gameControl, PlayerSelection playerSelection) {
        this.gameControl = gameControl;
        this.playerSelection = playerSelection;
    }

    @Override
    @NullMarked
    public void run(String... args) {
        ConsoleOutput.section("Hibernate started successfully.");
        gameControl.gameLoop(playerSelection.getPlayers());
    }
}
