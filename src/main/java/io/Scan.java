package io;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.IO.println;

public class Scan {
    private final Scanner scanner = new Scanner(System.in);

    public boolean continueGame() {
        println("\nMöchtest du ein neues Spiel starten?");
        println("Bitte true oder false eingeben");
        try {
            return scanner.nextBoolean();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            println("\nEingabe konnte nicht gelesen werden");
            return continueGame();
        }
    }
}
