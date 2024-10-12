package com.battleship.game.model;


import java.util.HashMap;
import java.util.Map;

public class Battlefield {
    private final int size; // Size of the battlefield (for a square grid)
    private final int[][] leftArea;
    private final int[][] rightArea;

    public Battlefield(int size) {
        this.size = size; // Initialize the size
        this.leftArea = new int[size][size]; // Create a square grid for the left area
        this.rightArea = new int[size][size]; // Create a square grid for the right area
    }

    public int getSize() {
        return size; // Return the size of the battlefield
    }

    public int[][] getLeftArea() {
        return leftArea; // Return the left player's battlefield
    }

    public int[][] getRightArea() {
        return rightArea; // Return the right player's battlefield
    }
}
