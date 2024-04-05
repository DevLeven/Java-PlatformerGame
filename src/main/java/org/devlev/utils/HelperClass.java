package org.devlev.utils;

import org.devlev.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HelperClass {

    public static boolean canEntityPassThrough(float xPos, float yPos, float width, float height, int[][] levelData)
    {
        return !isSolid(xPos, yPos, levelData) &&
                !isSolid(xPos + width, yPos + height, levelData) &&
                !isSolid(xPos + width, yPos, levelData) &&
                !isSolid(xPos, yPos + height, levelData);
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float entityHitbox, float xSpeed)
    {
        int currentTile = (int) (entityHitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0)
        {
            int tileXPosition = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - entityHitbox.width);
            return tileXPosition + xOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosNextToTiles(Rectangle2D.Float entityHitbox, float airSpeed)
    {
        int currentTile = (int) (entityHitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0)
        {
            int tileYPosition = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - entityHitbox.height);
            return tileYPosition + yOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    private static boolean isSolid(float xPos, float yPos, int[][] levelData)
    {
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (xPos < 0 || xPos >= maxWidth)
            return true;
        if (yPos < 0 || yPos >= Game.GAME_HEIGHT)
            return true;

        float xIndex = xPos / Game.TILES_SIZE;
        float yIndex = yPos / Game.TILES_SIZE;

        int indexValue = levelData[(int) yIndex][(int) xIndex];

        return indexValue != 11;//Places entities cant pass through
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float entityHitbox, int[][] levelData)
    {
        return !(!isSolid(entityHitbox.x, entityHitbox.y + entityHitbox.height + 1, levelData) &&
                !isSolid(entityHitbox.x + entityHitbox.width, entityHitbox.y + entityHitbox.height + 1, levelData));
    }
}
