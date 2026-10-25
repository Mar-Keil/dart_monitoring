package game;

import console.ConsoleInput;
import console.ConsoleOutput;
import enums.CheckoutRule;
import model.Game;
import model.Turn;
import persistence.TurnRepository;

public class TurnControl {

    private final TurnRepository turnRepository;
    private final ConsoleInput input;

    public TurnControl(TurnRepository turnRepository, ConsoleInput input) {
        this.turnRepository = turnRepository;
        this.input = input;
    }

    public boolean recordThrow(Game game, int turnNumber){

        Turn turn = new Turn(game, turnNumber);

        ConsoleOutput.section(game.getPlayer().getFirstname() + " " + game.getPlayer().getLastname() + ": " + game.getRemainingScore());
        String[] validatedScores = input.readPoints("Please enter score. Required format: t20, d10, 25");

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
