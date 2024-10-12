package com.battleship.game.dto;

import lombok.Data;

@Data
public class FireRequestDTO {
	
	private String playerName; // The player firing the missile
    private int x; // X coordinate
    private int y; // Y coordinate
    private String gameId; // Game ID


	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
    
    
}
