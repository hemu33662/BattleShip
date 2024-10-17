package com.battleship.game.dto;

import lombok.Data;


@Data
public class GameDTO {
    private int gridSize;
    private int numberOfShips;
    private String player1; // Updated to match the player naming
    private String player2; // Updated to match the player naming

    // Constructor
    public GameDTO(int gridSize, int numberOfShips, String player1, String player2) {
        this.gridSize = gridSize;
        this.numberOfShips = numberOfShips;
        this.player1 = player1; // Initialize player1
        this.player2 = player2; // Initialize player2
    }

    // No-argument constructor for use in deserialization
    public GameDTO() {
    }

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getNumberOfShips() {
		return numberOfShips;
	}

	public void setNumberOfShips(int numberOfShips) {
		this.numberOfShips = numberOfShips;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
    
    
}
