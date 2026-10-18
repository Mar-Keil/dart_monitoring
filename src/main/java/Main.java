import game.Control;
import io.*;

public class Main {
    public void main(String[] args){
        Control controller = new Control(new Printer(), new Reader(), new Scan());
    }
}
