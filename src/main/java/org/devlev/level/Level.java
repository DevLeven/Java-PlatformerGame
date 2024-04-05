package org.devlev.level;

public class Level {

    private int[][] levelData; //Save level data

    public Level(int[][] levelData)
    {
        this.levelData = levelData; //Initialize level data from constructor variable
    }

    public int getSpriteIndex(int x, int y) {return this.levelData[y][x];}

    public int[][] getLevelData() {
        return this.levelData;
    }
}
