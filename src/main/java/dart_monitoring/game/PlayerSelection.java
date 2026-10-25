package dart_monitoring.game;

import dart_monitoring.console.ConsoleInput;
import dart_monitoring.console.ConsoleOutput;
import dart_monitoring.model.Player;
import dart_monitoring.persistence.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerSelection {

    private final ConsoleInput input;
    private final PlayerRepository playerRepository;

    public PlayerSelection(PlayerRepository playerRepository, ConsoleInput input) {
        this.input = input;
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {

        ConsoleOutput.section("Known players:");

        List<Player> allPlayers = playerRepository.findAll();

        int nameWidth = 0;
        for (Player player : allPlayers) {
            nameWidth = Math.max(nameWidth, (player.getFirstname() + " " + player.getLastname()).length());
        }
        for (Player player : allPlayers) {
            System.out.printf("  %-" + nameWidth + "s : %d%n",
                    player.getFirstname() + " " + player.getLastname(), player.getId());
        }
        return selectPlayer(allPlayers, new ArrayList<>());
    }


    private List<Player> selectPlayer(List<Player> allPlayers, List<Player> selectedPlayers) {

        while (true) {
            String choice = input.readWord("Enter player id or enter new to add a new player:");
            if (choice.equalsIgnoreCase("new")) {
                selectedPlayers.add(addPlayer());
            } else {
                int id;
                try {
                    id = Integer.parseInt(choice);
                } catch (NumberFormatException e) {
                    ConsoleOutput.error("Invalid input. Please try again.");
                    continue;
                }

                Player selectedPlayer = null;
                for (Player player : allPlayers) {
                    if (id == player.getId()) {
                        selectedPlayer = player;
                        break;
                    }
                }
                if (selectedPlayer == null) {
                    ConsoleOutput.error("Player " + id + " is not a valid player ID. Try again.");
                    continue;
                }
                selectedPlayers.add(selectedPlayer);
                allPlayers.remove(selectedPlayer);
            }

            if (!input.readConfirmation("Do you want to add another player?")) {
                return selectedPlayers;
            }
        }
    }


    private Player addPlayer() {
        Player newPlayer = new Player();
        do{
            newPlayer.setFirstname(input.readWord("Please enter the first name:"));

            do {
                newPlayer.setLastname(input.readLine("Please enter the last name:"));
                if (newPlayer.getLastname().isEmpty()) ConsoleOutput.error("Last name cannot be empty.");
            } while (newPlayer.getLastname().isBlank());

        } while(!input.readConfirmation("Do you want to add " + newPlayer.getFirstname() + " " + newPlayer.getLastname() + " as a new player?"));
        playerRepository.save(newPlayer);
        return newPlayer;
    }
}
