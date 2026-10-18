package game;

import model.Player;
import persistence.PlayerRepository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PlayerSelection {

    private final Scanner scanner;
    private final PlayerRepository playerRepository;

    public PlayerSelection(PlayerRepository playerRepository, Scanner scanner) {
        this.scanner = scanner;
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {

        IO.println("Known players:");

        List<Player> allPlayers = playerRepository.findAll();

        for (Player player : allPlayers) {
            IO.println(player.getFirstname() + " " + player.getLastname() + ": " + player.getId());
        }
        return selectPlayer(allPlayers, new ArrayList<>());
    };


    private List<Player> selectPlayer(List<Player> allPlayers, List<Player> selectedPlayers) {

        IO.println("\nEnter player id or enter new to add a new player:");

        try{
            int id = scanner.nextInt();
            scanner.nextLine();

            for(Player player : allPlayers){
                if(id == player.getId()){
                    selectedPlayers.add(player);
                    allPlayers.remove(player);

                    IO.println("Do you want to add another player?");
                    if (readBoolean()){
                        return selectPlayer(allPlayers, selectedPlayers);
                    }
                    else{
                        return selectedPlayers;
                    }
                }
            }

            IO.println("Player " + id + " is not a valid player ID. Try again.");
            return selectPlayer(allPlayers, selectedPlayers);

        } catch (InputMismatchException e) {
            if (scanner.nextLine().strip().equalsIgnoreCase("new")){
                 selectedPlayers.add(addPlayer());
                 IO.println("Do you want to add another player?");
                 if (readBoolean()) return selectPlayer(allPlayers, selectedPlayers);
                 else return selectedPlayers;
            }
            IO.println("Invalid input. Please try again.");
            return selectPlayer(allPlayers, selectedPlayers);
        }
    }


    private Player addPlayer() {
        Player newPlayer = new Player();
        do{
            IO.println("Pleas enter the first name:");
            newPlayer.setFirstname(scanner.next());
            scanner.nextLine(); // Needed to have the next line as new input

            do {
                IO.println("Pleas enter the last name:");
                newPlayer.setLastname(scanner.nextLine().strip());
                if (newPlayer.getLastname().isEmpty()) IO.println("Last name cannot be empty.");
            } while (newPlayer.getLastname().isBlank());

            IO.println("Do you want to add " +  newPlayer.getFirstname() + " " + newPlayer.getLastname() + " as a new player?");
        } while(!readBoolean());
        playerRepository.save(newPlayer);
        return newPlayer;
    }


    private boolean readBoolean() {
        try{
            IO.println("Please enter true or false:");
            return switch (scanner.nextLine().strip().toLowerCase(Locale.ROOT)) {
                case "true" -> true;
                case "false" -> false;
                default -> throw new InputMismatchException();
            };
        } catch (InputMismatchException e) {
            IO.println("Invalid input. Please try again.");
            return readBoolean();
        }
    }
}
