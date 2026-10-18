package game;

import io.*;
import model.Player;

import java.util.List;

public class GameControl {

    private final Scan scan =  new Scan();

    public void startGame(List<Player> players){
        boolean active = true;
        do {

            CurrentGame game = new CurrentGame(scan);

        }while(scan.continueGame());

    }
}
