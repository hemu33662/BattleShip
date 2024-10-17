//package com.battleship.game.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//import org.springframework.stereotype.Service;
//
//import com.battleship.game.dto.FireRequestDTO;
//import com.battleship.game.dto.GameDTO;
//import com.battleship.game.dto.ShipDTO;
//import com.battleship.game.exception.GameNotFoundException;
//import com.battleship.game.model.Game;
//import com.battleship.game.model.Player;
//import com.battleship.game.model.Ship;
//
//@Service
//public class BattleshipService {
//
//    private final ConcurrentHashMap<Long, Game> games = new ConcurrentHashMap<>();
//    private final AtomicLong gameIdCounter = new AtomicLong(1);
//
//    // Create a new game
//    public Game createGame(GameDTO gameDTO, String creatorName) {
//        Long gameId = gameIdCounter.getAndIncrement();
//        Game game = new Game();
//        game.setGameId(gameId);
//        game.setGridSize(gameDTO.getGridSize());
//        game.setNumberOfShips(gameDTO.getNumberOfShips());
//        game.setStatus("IN_PROGRESS");
//        game.setCurrentTurn("player1");
//
//        Player player1 = new Player();
//        player1.setName("Player1"); // Name from the request
//        // No need to set ships and shotsFired here as they're initialized in the Player class
//
//        Player player2 = new Player();
//        player2.setName("Player2"); // Name can also be set from request if needed
//
//        game.setPlayer1(player1);
//        game.setPlayer2(player2);
//
//        games.put(gameId, game);
//        return game;
//    }
//
//    // Place a ship for a player
//    public void placeShip(Long gameId, String playerName, ShipDTO shipDTO) {
//        Game game = getGame(gameId);
//        Player player = getPlayerByName(game, playerName);
//        Ship ship = new Ship();
//        ship.setShipId(shipDTO.getShipId());
//        ship.setSize(shipDTO.getSize());
//        ship.setCoordinates(shipDTO.getCoordinates());
//        ship.setRemainingParts(shipDTO.getSize());
//
//        System.out.println("Player:"+player.getName());
//        player.getShips().add(ship);
//    }
//
//    // Fire a missile
//    public String fireMissile(Long gameId, String playerName, FireRequestDTO request) {
//        Game game = getGame(gameId);
//        validateTurn(game, playerName);
//
//        Player attacker = getPlayerByName(game, playerName);
//        Player defender = getOpponentPlayer(game, attacker);
//
//        String coordinateKey = request.getX() + "," + request.getY();
//        boolean hit = checkHit(defender, request.getX(), request.getY());
//
//        // Store the shot in the attacker's shots fired map
//        if (attacker.getShotsFired() == null) {
//            attacker.setShotsFired(new ConcurrentHashMap<>());
//        }
//        attacker.getShotsFired().put(coordinateKey, hit);
//
//        // Check for game completion
//        if (checkGameCompletion(defender)) {
//            game.setStatus("COMPLETED");
//            return "Hit! " + attacker.getName() + " wins!";
//        }
//
//        // Switch turn to the defender for the next round
//        game.setCurrentTurn(defender.getName());
//        
//        // Debug output to verify turn switch
//        System.out.println("Turn switched to: " + game.getCurrentTurn());
//        
//        return hit ? "Hit!" : "Miss!";
//    }
//
//
//    // Helper methods
//    private Game getGame(Long gameId) {
//        Game game = games.get(gameId);
//        if (game == null) throw new GameNotFoundException("Game not found");
//        return game;
//    }
//
//    private void validateTurn(Game game, String playerName) {
//        String currentTurn = game.getCurrentTurn().toLowerCase(); // Normalize to lowercase
//        String playerRole = getPlayerRole(game, playerName).toLowerCase(); // Normalize to lowercase
//        
//        System.out.println("Current turn: " + currentTurn);
//        System.out.println("Player role: " + playerRole);
//
//        if (!currentTurn.equals(playerRole)) {
//            throw new IllegalArgumentException("It's not your turn.");
//        }
//    }
//
//
//    private Player getPlayerByName(Game game, String playerName) {
//        if (game.getPlayer1().getName().equals(playerName)) {
//            return game.getPlayer1();
//        } else if (game.getPlayer2().getName().equals(playerName)) {
//            return game.getPlayer2();
//        }
//        throw new IllegalArgumentException("Player not found");
//    }
//
//    private boolean checkHit(Player defender, int x, int y) {
//        for (Ship ship : defender.getShips()) {
//            for (int[] coord : ship.getCoordinates()) {
//                if (coord[0] == x && coord[1] == y) {
//                    ship.setRemainingParts(ship.getRemainingParts() - 1);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean checkGameCompletion(Player defender) {
//        return defender.getShips().stream().allMatch(ship -> ship.getRemainingParts() <= 0);
//    }
//
//    private String getPlayerRole(Game game, String playerName) {
//        if (game == null || playerName == null) {
//            throw new IllegalArgumentException("Game or player name cannot be null");
//        }
//
//        String player1Name = game.getPlayer1() != null ? game.getPlayer1().getName() : null;
//        String player2Name = game.getPlayer2() != null ? game.getPlayer2().getName() : null;
//
//        if (player1Name != null && player1Name.equalsIgnoreCase(playerName)) {
//            System.out.println("Player1");
//            return "player1";
//        } else if (player2Name != null && player2Name.equalsIgnoreCase(playerName)) {
//            System.out.println("Player2"); // Fixed this line
//            return "player2";
//        } else {
//            throw new IllegalArgumentException("Player not found in the game");
//        }
//    }
//
//
//
//    private Player getOpponentPlayer(Game game, Player player) {
//        return player.equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
//    }
//}
package com.battleship.game.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.battleship.game.dto.FireRequestDTO;
import com.battleship.game.dto.GameDTO;
import com.battleship.game.dto.ShipDTO;
import com.battleship.game.exception.GameNotFoundException;
import com.battleship.game.model.Game;
import com.battleship.game.model.Player;
import com.battleship.game.model.Ship;

@Service
public class BattleshipService {

    private final ConcurrentHashMap<Long, Game> games = new ConcurrentHashMap<>();
    private final AtomicLong gameIdCounter = new AtomicLong(1);

    // Create a new game
 // Create a new game
    public Game createGame(GameDTO gameDTO) {
        Long gameId = gameIdCounter.getAndIncrement();
        Game game = new Game();
        game.setGameId(gameId);
        game.setGridSize(gameDTO.getGridSize());
        game.setNumberOfShips(gameDTO.getNumberOfShips());
        game.setStatus("IN_PROGRESS");
        game.setCurrentTurn(gameDTO.getPlayer1()); // Set the current turn to player1

        Player player1 = new Player();
        player1.setName(gameDTO.getPlayer1()); // Set name from DTO

        Player player2 = new Player();
        player2.setName(gameDTO.getPlayer2()); // Set name from DTO

        game.setPlayer1(player1);
        game.setPlayer2(player2);

        games.put(gameId, game);
        
        System.out.println("GameID"+ gameId);
        System.out.println("GridSize"+ gameDTO.getGridSize());
        System.out.println("No:of Ships"+ gameDTO.getNumberOfShips());
        System.out.println("Player1"+gameDTO.getPlayer1());
        System.out.println("Player2"+gameDTO.getPlayer2());
        return game;
    }

    public void placeShips(Long gameId, String playerName, int allowedShips, List<ShipDTO> shipDTOs) {
        Game game = getGame(gameId);
        Player player = getPlayerByName(game, playerName);
        
        // Check if the number of ships to place exceeds the allowed limit
        if (shipDTOs.size() > allowedShips) {
            throw new IllegalArgumentException("Cannot place more ships than allowed. Allowed: " + allowedShips + ", Attempted: " + shipDTOs.size());
        }

        // Place the ships
        for (ShipDTO shipDTO : shipDTOs) {
            Ship ship = new Ship();
            ship.setShipId(shipDTO.getShipId());
            ship.setSize(shipDTO.getSize());
            ship.setCoordinates(shipDTO.getCoordinates());
            ship.setRemainingParts(shipDTO.getSize());

            System.out.println("Player: " + player.getName());
            player.getShips().add(ship);
        }
    }


    public String fireMissile(Long gameId, String playerName, FireRequestDTO request) {
        Game game = getGame(gameId);
        System.out.println("Fire Missile: " + playerName);
        validateTurn(game, playerName);

        Player attacker = getPlayerByName(game, playerName);
        Player defender = getOpponentPlayer(game, attacker);

        // Create the coordinate key
        String coordinateKey = request.getX() + "," + request.getY();

        // Check if the attacker has already fired at this coordinate
        if (attacker.getShotsFired() != null && attacker.getShotsFired().containsKey(coordinateKey)) {
            throw new IllegalArgumentException("You have already fired at this coordinate: " + coordinateKey);
        }

        // Check for hit
        boolean hit = checkHit(defender, request.getX(), request.getY());

        // Initialize shots fired map if null
        if (attacker.getShotsFired() == null) {
            attacker.setShotsFired(new ConcurrentHashMap<>());
        }

        // Record the shot
        attacker.getShotsFired().put(coordinateKey, hit);

        // Logging the shot
        System.out.println("Attacker: " + attacker.getName() + ", Coordinate: " + coordinateKey + ", Hit: " + hit);

        // Check for game completion
        if (checkGameCompletion(defender)) {
            game.setStatus("COMPLETED");
            
            // Print defender's ships
            System.out.println("\u001B[33m" + defender.getName() + "'s ships were at: \u001B[0m");
            for (Ship ship : defender.getShips()) {
                String shipName = ship.getShipId();
                List<int[]> coordinates = ship.getCoordinates();

                // Format the coordinates
                StringBuilder coordinateString = new StringBuilder("[");
                for (int[] coord : coordinates) {
                    coordinateString.append(Arrays.toString(coord)).append(", ");
                }
                if (coordinateString.length() > 1) {
                    coordinateString.setLength(coordinateString.length() - 2);
                }
                coordinateString.append("]");

                System.out.println("\u001B[33mShip ID: " + shipName + " at coordinates: " + coordinateString.toString() + "\u001B[0m");
            }

            // Print attacker's ships
            System.out.println("\u001B[33m" + attacker.getName() + "'s ships were at: \u001B[0m");
            for (Ship ship : attacker.getShips()) {
                String shipName = ship.getShipId();
                List<int[]> coordinates = ship.getCoordinates();

                // Format the coordinates
                StringBuilder coordinateString = new StringBuilder("[");
                for (int[] coord : coordinates) {
                    coordinateString.append(Arrays.toString(coord)).append(", ");
                }
                if (coordinateString.length() > 1) {
                    coordinateString.setLength(coordinateString.length() - 2);
                }
                coordinateString.append("]");

                System.out.println("\u001B[33mShip ID: " + shipName + " at coordinates: " + coordinateString.toString() + "\u001B[0m");
            }

            return "Hit! " + attacker.getName() + " wins!";
        }

        // Switch turns
        game.setCurrentTurn(defender.getName());

        return hit ? "Hit!" : "Miss!";
    }




    // Helper methods
    private Game getGame(Long gameId) {
        Game game = games.get(gameId);
        if (game == null) throw new GameNotFoundException("Game not found");
        return game;
    }

    private void validateTurn(Game game, String playerName) {
    	System.out.println("ValidageTurn:"+playerName);
        String currentTurn = game.getCurrentTurn(); // Normalize to lowercase
        String playerRole = getPlayerRole(game, playerName); // Normalize to lowercase
        
        System.out.println("Current turn: " + currentTurn);
        System.out.println("Player role: " + playerRole);

        if (!currentTurn.equals(playerRole)) {
            throw new IllegalArgumentException("It's not your turn.");
        }
    }


    private Player getPlayerByName(Game game, String playerName) {
        if (game.getPlayer1().getName().equals(playerName)) {
            return game.getPlayer1();
        } else if (game.getPlayer2().getName().equals(playerName)) {
            return game.getPlayer2();
        }
        throw new IllegalArgumentException("Player not found");
    }

    private boolean checkHit(Player defender, int x, int y) {
        for (Ship ship : defender.getShips()) {
            for (int[] coord : ship.getCoordinates()) {
                if (coord[0] == x && coord[1] == y) {
                    ship.setRemainingParts(ship.getRemainingParts() - 1);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkGameCompletion(Player defender) {
        return defender.getShips().stream().allMatch(ship -> ship.getRemainingParts() <= 0);
    }

    private String getPlayerRole(Game game, String playerName) {
    	
    	System.out.println("Getplayer:"+playerName);
    	
        if (game == null || playerName == null) {
            throw new IllegalArgumentException("Game or player name cannot be null");
        }

        String player1Name = game.getPlayer1() != null ? game.getPlayer1().getName() : null;
        String player2Name = game.getPlayer2() != null ? game.getPlayer2().getName() : null;

        if (player1Name != null && player1Name.equalsIgnoreCase(playerName)) {
            System.out.println("Player1");
            return playerName;
        } else if (player2Name != null && player2Name.equalsIgnoreCase(playerName)) {
            System.out.println("Player2"); // Fixed this line
            return playerName;
        } else {
            throw new IllegalArgumentException("Player not found in the game");
        }
    }



    private Player getOpponentPlayer(Game game, Player player) {
        return player.equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
    }
}
