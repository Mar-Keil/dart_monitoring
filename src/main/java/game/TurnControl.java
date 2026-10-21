package game;


import enums.CheckoutRule;
import model.Game;
import model.Turn;
import persistence.TurnRepository;

import java.util.Scanner;

public class TurnControl {

    private final TurnRepository turnRepository;
    private final Scanner scanner;

    public TurnControl(TurnRepository turnRepository, Scanner scanner) {
        this.turnRepository = turnRepository;
        this.scanner = scanner;
    }

    public boolean recordThrow(Game game, int turnNumber){

        Turn turn = new Turn(game, turnNumber);

        IO.println(game.getPlayer().getFirstname() + " " + game.getPlayer().getLastname() + ": " + game.getRemainingScore());
        IO.println("Please enter score. Required format: t20, d10, 25");

        String[] validatedScores = new String[3];

        for (int i = 0; i < 3; i++){
            String score = scanner.next().replaceFirst(",$", "").toLowerCase(java.util.Locale.ROOT);
            if (!isValidScore(score)){
                IO.println("Score " + (i + 1) + " is invalid. Please try again.");
                return recordThrow(game, turnNumber);
            }
            validatedScores[i] = score;
        }

        String[] calculatedScores = new String[3];
        int remainingScore = game.getRemainingScore();
        boolean doubleOut = game.getCheckoutRule() == CheckoutRule.DOUBLE_OUT;
        boolean bust = false;

        for (int i = 0; i < 3; i++){
            String score = validatedScores[i];
            calculatedScores[i] = score;
            remainingScore -= parseScore(score);

            boolean validCheckout = !doubleOut || score.startsWith("d");

            if (remainingScore < 0
                    || (doubleOut && remainingScore == 1)
                    || (remainingScore == 0 && !validCheckout)){
                bust = true;
                break;
            }

            if (remainingScore == 0){
                turn.setFinisher(true);
                break;
            }
        }

        turn.setBust(bust);
        if (!bust) game.setRemainingScore(remainingScore);
        turn.setThrows(calculatedScores);
        turnRepository.save(turn);

        return game.getRemainingScore() == 0;
    }

    private boolean isValidScore(String score) {
        return score.matches("([dt]([1-9]|1[0-9]|20)|[0-9]|1[0-9]|20|25|d25)");
    }

    private int parseScore(String score){
        if(score.startsWith("t")){
            return Integer.parseInt(score.substring(1)) * 3;
        }
        else if(score.startsWith("d")){
            return Integer.parseInt(score.substring(1)) * 2;
        }
        else {
            return Integer.parseInt(score);
        }
    }
}
