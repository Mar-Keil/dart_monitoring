package dart_monitoring.console;

import dart_monitoring.enums.BotDifficulty;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleInput {

    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        ConsoleOutput.prompt(prompt);
        return scanner.nextLine().strip();
    }

    public String readWord(String prompt) {
        while (true) {
            String word = readLine(prompt);
            if (word.matches("\\S+")) {
                return word;
            }
            ConsoleOutput.error("Please enter a single word.");
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                ConsoleOutput.error("Please enter a valid integer.");
            }
        }
    }

    public BotDifficulty readBotDifficulty() {
        while (true) {
            int choice = readInt("Select bot difficulty:\n1 - Easy\n2 - Medium\n3 - Hard");
            switch (choice) {
                case 1 -> {
                    return BotDifficulty.EASY;
                }
                case 2 -> {
                    return BotDifficulty.MEDIUM;
                }
                case 3 -> {
                    return BotDifficulty.HARD;
                }
                default -> ConsoleOutput.error("Please select 1, 2 or 3.");
            }
        }
    }

    public String[] readPoints(String prompt) {
        while (true) {
            String[] scores = readLine(prompt).toLowerCase(Locale.ROOT).split("\\s+");
            if (scores.length != 3) {
                ConsoleOutput.error("Please enter exactly three scores on one line.");
                continue;
            }

            boolean valid = true;
            for (int i = 0; i < scores.length; i++) {
                scores[i] = scores[i].replaceFirst(",$", "");
                if (!scores[i].matches("([dt]([1-9]|1[0-9]|20)|[0-9]|1[0-9]|20|25|d25)")) {
                    ConsoleOutput.error("Score " + (i + 1) + " is invalid. Please try again.");
                    valid = false;
                    break;
                }
            }
            if (valid) {
                return scores;
            }
        }
    }

    public boolean readConfirmation(String question) {
        while (true) {
            String answer = readLine(question + "\nPress Enter for yes or type no:");
            if (answer.isEmpty()) {
                return true;
            }
            if (answer.equalsIgnoreCase("no")) {
                return false;
            }
            ConsoleOutput.error("Invalid input. Please try again.");
        }
    }
}
