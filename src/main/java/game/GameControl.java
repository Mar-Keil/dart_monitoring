package game;

import enums.CheckoutRule;
import model.*;
import persistence.GameRepository;
import persistence.TurnRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameControl {

    GameRepository gameRepository;
    TurnRepository turnRepository;
    Scanner scanner;

    public GameControl(GameRepository gameRepository, TurnRepository turnRepository, Scanner scanner) {
        this.gameRepository = gameRepository;
        this.turnRepository = turnRepository;
        this.scanner = scanner;

    }

    public void startGame(List<Player> players){

        List<Game> currentGames = initializeGames(players);

       TurnControl turnControl = new TurnControl(turnRepository, scanner);
       int turnNumber = 1;

       do{
           for  (Game game : currentGames){
               if(turnControl.recordThrow(game, turnNumber)){
                   saveGames(currentGames);
                   return;
               }
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
