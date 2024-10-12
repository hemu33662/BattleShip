package com.battleship.game.model;

import lombok.Data;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Game {
    private Long gameId;
    private String status; // e.g., IN_PROGRESS, COMPLETED
    private int gridSize;
    private int numberOfShips;
    private Player player1;
    private Player player2;
    private volatile String currentTurn; // "player1" or "player2"
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Player getPlayer1() {
		return player1;
	}
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	public String getCurrentTurn() {
		return currentTurn;
	}
	public void setCurrentTurn(String currentTurn) {
		this.currentTurn = currentTurn;
	}
    
    
}