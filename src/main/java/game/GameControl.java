package game;

import console.ConsoleInput;
import console.ConsoleOutput;
import enums.CheckoutRule;
import model.*;
import persistence.GameRepository;
import persistence.TurnRepository;

import java.util.*;

public class GameControl {

    GameRepository gameRepository;
    TurnRepository turnRepository;
    private final ConsoleInput input;

    public GameControl(GameRepository gameRepository, TurnRepository turnRepository, ConsoleInput input) {
        this.gameRepository = gameRepository;
        this.turnRepository = turnRepository;
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

       TurnControl turnControl = new TurnControl(turnRepository, input);
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
