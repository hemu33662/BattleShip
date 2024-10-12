package com.battleship.game.dto;

import lombok.Data;
import java.util.List;

@Data
public class ShipDTO {
    private String shipId;
    private int size;
    private List<int[]> coordinates;
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
    
    
}
