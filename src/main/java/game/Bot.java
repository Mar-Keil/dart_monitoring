package game;

import console.ConsoleInput;
import console.ConsoleOutput;
import enums.BotDifficulty;

import java.util.Random;

public class Bot {

    private final BotDifficulty difficulty;
    private final Random random = new Random();
    private int points;

    public Bot(ConsoleInput input) {
        this.difficulty = input.readBotDifficulty();
        points = 501;
    }

    public boolean turn() {
        if (points == 0) {
            return true;
        }

        ConsoleOutput.section("Bot (" + difficulty + "): " + points);
        int score = 0;

        if (canCheckout()) {
            if (random.nextDouble() < difficulty.getCheckoutChance()) {
                IO.println("Checkout: " + (points == 50 ? "Bullseye" : "D" + points / 2));
                score = points;
            } else {
                IO.println("Checkout missed.");
            }
        } else {
            int maximum = Math.min(difficulty.getMaxScore(), points - 2);
            int minimum = Math.min(difficulty.getMinScore(), maximum);
            score = random.nextInt(minimum, maximum + 1);
        }

        points -= score;
        IO.println("Scored: " + score);
        IO.println("Remaining: " + points);
        return points == 0;
    }

    public int getRemainingScore() {
        return points;
    }

    private boolean canCheckout() {
        return (points >= 2 && points <= 40 && points % 2 == 0) || points == 50;
    }
}
