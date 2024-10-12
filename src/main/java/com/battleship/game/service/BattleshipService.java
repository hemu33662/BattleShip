package com.battleship.game.service;

import java.util.ArrayList;
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
    public Game createGame(GameDTO gameDTO, String creatorName) {
        Long gameId = gameIdCounter.getAndIncrement();
        Game game = new Game();
        game.setGameId(gameId);
        game.setGridSize(gameDTO.getGridSize());
        game.setNumberOfShips(gameDTO.getNumberOfShips());
        game.setStatus("IN_PROGRESS");
        game.setCurrentTurn("player1");

        Player player1 = new Player();
        player1.setName("Player1"); // Name from the request
        // No need to set ships and shotsFired here as they're initialized in the Player class

        Player player2 = new Player();
        player2.setName("Player2"); // Name can also be set from request if needed

        game.setPlayer1(player1);
        game.setPlayer2(player2);

        games.put(gameId, game);
        return game;
    }

    // Place a ship for a player
    public void placeShip(Long gameId, String playerName, ShipDTO shipDTO) {
        Game game = getGame(gameId);
        Player player = getPlayerByName(game, playerName);
        Ship ship = new Ship();
        ship.setShipId(shipDTO.getShipId());
        ship.setSize(shipDTO.getSize());
        ship.setCoordinates(shipDTO.getCoordinates());
        ship.setRemainingParts(shipDTO.getSize());

        player.getShips().add(ship);
    }

    // Fire a missile
    public String fireMissile(Long gameId, String playerName, FireRequestDTO request) {
        Game game = getGame(gameId);
        validateTurn(game, playerName);

        Player attacker = getPlayerByName(game, playerName);
        Player defender = getOpponentPlayer(game, attacker);

        String coordinateKey = request.getX() + "," + request.getY();
        boolean hit = checkHit(defender, request.getX(), request.getY());

        // Store the shot in the attacker's shots fired map
        if (attacker.getShotsFired() == null) {
            attacker.setShotsFired(new ConcurrentHashMap<>());
        }
        attacker.getShotsFired().put(coordinateKey, hit);

        // Check for game completion
        if (checkGameCompletion(defender)) {
            game.setStatus("COMPLETED");
            return "Hit! " + attacker.getName() + " wins!";
        }

        // Switch turn to the defender for the next round
        game.setCurrentTurn(defender.getName());
        
        // Debug output to verify turn switch
        System.out.println("Turn switched to: " + game.getCurrentTurn());
        
        return hit ? "Hit!" : "Miss!";
    }


    // Helper methods
    private Game getGame(Long gameId) {
        Game game = games.get(gameId);
        if (game == null) throw new GameNotFoundException("Game not found");
        return game;
    }

    private void validateTurn(Game game, String playerName) {
        String currentTurn = game.getCurrentTurn().toLowerCase(); // Normalize to lowercase
        String playerRole = getPlayerRole(game, playerName).toLowerCase(); // Normalize to lowercase
        
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
        if (game == null || playerName == null) {
            throw new IllegalArgumentException("Game or player name cannot be null");
        }

        String player1Name = game.getPlayer1() != null ? game.getPlayer1().getName() : null;
        String player2Name = game.getPlayer2() != null ? game.getPlayer2().getName() : null;

        if (player1Name != null && player1Name.equalsIgnoreCase(playerName)) {
            System.out.println("Player1");
            return "player1";
        } else if (player2Name != null && player2Name.equalsIgnoreCase(playerName)) {
            System.out.println("Player2"); // Fixed this line
            return "player2";
        } else {
            throw new IllegalArgumentException("Player not found in the game");
        }
    }



    private Player getOpponentPlayer(Game game, Player player) {
        return player.equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1();
    }
}
