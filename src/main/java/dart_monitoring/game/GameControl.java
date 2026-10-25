package dart_monitoring.game;

import dart_monitoring.console.ConsoleInput;
import dart_monitoring.console.ConsoleOutput;
import dart_monitoring.enums.CheckoutRule;
import dart_monitoring.model.*;
import dart_monitoring.persistence.GameRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GameControl {

    private final GameRepository gameRepository;
    private final TurnControl turnControl;
    private final ConsoleInput input;

    public GameControl(GameRepository gameRepository, TurnControl turnControl, ConsoleInput input) {
        this.gameRepository = gameRepository;
        this.turnControl = turnControl;
        this.input = input;

    }

    public void gameLoop(List<Player> players){
        do{
            Bot bot = null;
            boolean activeBot = false;

            if(input.readConfirmation("Do you want to play against a bot?")){
                bot  = new Bot(input);
                activeBot = true;
            }

            startGame(players, bot, activeBot);
        } while(input.readConfirmation("Do you want to play again?"));
    }

    public void startGame(List<Player> players, Bot bot, boolean activeBot){

       List<Game> currentGames = initializeGames(players);

       int turnNumber = 1;

       do{
           for  (Game game : currentGames){
               if(turnControl.recordThrow(game, turnNumber)){
                   saveGames(currentGames);
                   ConsoleOutput.section(game.getPlayer().getFirstname() + " " + game.getPlayer().getLastname() + " has won!");
                   return;
               }
           }
           if (activeBot && bot.turn()) {
               saveGames(currentGames);
               ConsoleOutput.section("Bot has won!");
               return;
           }
           turnNumber++;
       } while(true);
    }

    private List<Game> initializeGames(List<Player> players){
        List<Game> currentGames = new ArrayList<>();
        for  (Player player : players){
            currentGames.add(new Game(player, 501, CheckoutRule.DOUBLE_OUT));
            gameRepository.save(currentGames.getLast());
        }
        return currentGames;
    }

    private void saveGames(List<Game> currentGames){
        for  (Game game : currentGames){
            gameRepository.save(game);
        }
    }

}
