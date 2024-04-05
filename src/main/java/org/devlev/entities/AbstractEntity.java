package org.devlev.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class AbstractEntity {

    protected float xPos, yPos; //Sets the position of each entity on screen
    protected int width, height; //Sets the width and height of entity
    protected Rectangle2D.Float entityHitbox; //Sets entity hitbox

    public AbstractEntity(float xPos, float yPos, int width, int height)
    {
        //Initialize entity position and proportions
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        //initEntityHitbox(); //Sets the entity hitbox based on position and width and height
    }

    protected void initEntityHitbox(float xPos, float yPos, int width, int height)
    {
        this.entityHitbox = new Rectangle2D.Float(xPos, yPos, width, height);
    }

    protected void drawEntityHitbox(Graphics graphics) //For debug purposes only
    {
        graphics.setColor(Color.RED);
        graphics.drawRect((int) this.entityHitbox.x, (int) this.entityHitbox.y, (int) this.entityHitbox.width, (int) this.entityHitbox.height);
    }

    /*protected void updateEntityHitbox()
    {
        this.entityHitbox.x = (int) this.xPos;
        this.entityHitbox.y = (int) this.yPos;
    }*/

    public Rectangle2D.Float getEntityHitbox() {
        return this.entityHitbox;
    }
}
