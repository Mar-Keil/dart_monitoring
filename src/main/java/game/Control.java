package game;

import io.*;

public class Control {

    private Printer printer;
    private Reader reader;
    private Scan scan;

    public Control(Printer printer, Reader reader, Scan scan){
        this.printer = printer;
        this.reader = reader;
        this.scan = scan;
    }

    public void startGame(){
        boolean active = true;
        do {

            CurrentGame game = new CurrentGame(scan);

        }while(active);

    }
}
