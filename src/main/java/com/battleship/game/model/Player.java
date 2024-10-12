package com.battleship.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Player {
    private String name;
    private List<Ship> ships;
    private ConcurrentHashMap<String, Boolean> shotsFired; // Ensure this is a map to track shots fired.

    // Constructor
    public Player() {
        this.ships = new ArrayList<>();
        this.shotsFired = new ConcurrentHashMap<>(); // Initialize here
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public ConcurrentHashMap<String, Boolean> getShotsFired() {
        return shotsFired;
    }

    public void setShotsFired(ConcurrentHashMap<String, Boolean> shotsFired) {
        this.shotsFired = shotsFired;
    }
}
