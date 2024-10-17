package com.battleship.game.controller;

import com.battleship.game.dto.FireRequestDTO;
import com.battleship.game.dto.GameDTO;
import com.battleship.game.dto.ShipDTO;
import com.battleship.game.exception.GameNotFoundException;
import com.battleship.game.model.Game;
import com.battleship.game.service.BattleshipService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/battleship")
public class BattleshipController {

    @Autowired
    private BattleshipService battleshipService;
    
    Long gameId;

    // Create a new game
    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestParam int gridSize,
                                            @RequestParam int numberOfShips,
                                            @RequestParam String player1Name,
                                            @RequestParam String player2Name) { // Add player2Name
        GameDTO gameDTO = new GameDTO(gridSize, numberOfShips, player1Name, player2Name);
        Game newGame = battleshipService.createGame(gameDTO); // Pass DTO directly
        gameId = newGame.getGameId();
        System.out.println("New game created with ID: " + gameId);
        return ResponseEntity.ok(newGame);
    }

    @PostMapping("/place-ship/{playerName}/{numberOfShips}")
    public ResponseEntity<String> placeShips(@PathVariable String playerName,
                                             @PathVariable int numberOfShips,
                                             @RequestBody @Validated List<ShipDTO> shipDTOs) {
          try {
            // Pass gameId, playerName, and shipDTOs to the service method
            battleshipService.placeShips(gameId, playerName, numberOfShips, shipDTOs);
            return ResponseEntity.ok("Ships placed successfully."); // Move return statement here
        } catch (GameNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    


    // Fire a missile
    @PostMapping("/fire/{playerName}")
    public ResponseEntity<String> fireMissile(@PathVariable String playerName,
                                               @RequestBody @Validated FireRequestDTO fireRequest) {
        try {
            String result = battleshipService.fireMissile(gameId, playerName, fireRequest);
            return ResponseEntity.ok(result);
        } catch (GameNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
