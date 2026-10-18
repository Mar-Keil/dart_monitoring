package game;

import io.*;
import model.Player;

import java.util.List;

public class GameControl {

    public void startGame(List<Player> players){
       IO.println("For the game selected are:");
       for  (Player player : players){
           IO.println(player.getFirstname() + " " + player.getLastname());
       }
    }
}
