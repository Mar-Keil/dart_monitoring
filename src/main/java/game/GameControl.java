package game;

import enums.CheckoutRule;
import model.*;
import persistence.GameRepository;
import persistence.TurnRepository;

import java.util.*;

public class GameControl {

    GameRepository gameRepository;
    TurnRepository turnRepository;
    Scanner scanner;

    public GameControl(GameRepository gameRepository, TurnRepository turnRepository, Scanner scanner) {
        this.gameRepository = gameRepository;
        this.turnRepository = turnRepository;
        this.scanner = scanner;

    }

    public void gameLoop(List<Player> players){
        do{
            startGame(players);
            IO.println("Do you want to play again?");
        } while(readConfirmation());
    }

    public void startGame(List<Player> players){

       List<Game> currentGames = initializeGames(players);

       TurnControl turnControl = new TurnControl(turnRepository, scanner);
       int turnNumber = 1;

       do{
           for  (Game game : currentGames){
               if(turnControl.recordThrow(game, turnNumber)){
                   saveGames(currentGames);
                   IO.println(game.getPlayer().getFirstname() + " " + game.getPlayer().getLastname() + " hat gewonnen");
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

    private boolean readConfirmation() {
        try{
            IO.println("Press Enter for yes or type no:");
            return switch (scanner.nextLine().strip().toLowerCase(Locale.ROOT)) {
                case "" -> true;
                case "no" -> false;
                default -> throw new InputMismatchException();
            };
        } catch (InputMismatchException e) {
            IO.println("Invalid input. Please try again.");
            return readConfirmation();
        }
    }
}
