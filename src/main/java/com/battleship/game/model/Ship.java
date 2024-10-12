package com.battleship.game.model;

import lombok.Data;
import java.util.List;

@Data
public class Ship {
    private String shipId;
    private int size;
    private List<int[]> coordinates; // List of [x, y] positions
    private int remainingParts;
	public String getShipId() {
		return shipId;
	}
	public void setShipId(String shipId) {
		this.shipId = shipId;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<int[]> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<int[]> coordinates) {
		this.coordinates = coordinates;
	}
	public int getRemainingParts() {
		return remainingParts;
	}
	public void setRemainingParts(int remainingParts) {
		this.remainingParts = remainingParts;
	}
    
    
}