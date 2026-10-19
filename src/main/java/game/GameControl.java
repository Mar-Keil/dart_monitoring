package game;

import enums.CheckoutRule;
import model.*;
import persistence.GameRepository;

import java.util.ArrayList;
import java.util.List;

public class GameControl {

    GameRepository gameRepository;

    public GameControl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void startGame(List<Player> players){

        List<Game> currentGames = new ArrayList<>();

       IO.println("For the game selected are:");
       for  (Player player : players){

           currentGames.add(new Game(player, 501, CheckoutRule.DOUBLE_OUT));
           gameRepository.save(currentGames.getLast());

           IO.println(player.getFirstname() + " " + player.getLastname());
       }
    }
}
