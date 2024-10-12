package com.battleship.game.dto;

import lombok.Data;

@Data
public class GameDTO {
    private int gridSize;
    private int numberOfShips;
    
 // Constructor
    public GameDTO(int gridSize, int numberOfShips) {
        this.gridSize = gridSize;
        this.numberOfShips = numberOfShips;
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
    
    
}