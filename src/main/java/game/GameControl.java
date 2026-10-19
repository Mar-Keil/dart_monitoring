package game;

import model.*;
import persistence.GameRepository;

import java.util.List;

public class GameControl {

    GameRepository gameRepository;

    public GameControl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void startGame(List<Player> players){
       IO.println("For the game selected are:");
       for  (Player player : players){
           IO.println(player.getFirstname() + " " + player.getLastname());
       }
    }
}
