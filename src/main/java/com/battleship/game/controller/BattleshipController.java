package com.battleship.game.controller;

import com.battleship.game.dto.FireRequestDTO;
import com.battleship.game.dto.GameDTO;
import com.battleship.game.dto.ShipDTO;
import com.battleship.game.exception.GameNotFoundException;
import com.battleship.game.model.Game;
import com.battleship.game.service.BattleshipService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
                                            @RequestParam String player1Name) {
        Game newGame = battleshipService.createGame(new GameDTO(gridSize, numberOfShips), player1Name);
        gameId = newGame.getGameId();
        System.out.println("newGame:"+newGame.getGameId());
        return ResponseEntity.ok(newGame);
    }

    // Place ships for a player
    @PostMapping("/place-ship/{playerName}")
    public ResponseEntity<String> placeShips(@PathVariable String playerName,
                                             @RequestBody @Validated List<ShipDTO> shipDTOs) {
        try {
            for (ShipDTO shipDTO : shipDTOs) {
                battleshipService.placeShip(gameId, playerName, shipDTO);
            }
            return ResponseEntity.ok("Ships placed successfully.");
        } catch (GameNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
