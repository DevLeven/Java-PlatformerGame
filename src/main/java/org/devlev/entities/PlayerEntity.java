package org.devlev.entities;

import lombok.Setter;
import org.devlev.Game;
import org.devlev.utils.GameConstants.*;
import org.devlev.utils.GameFile;
import org.devlev.utils.HelperClass;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.AnimationConstants.*;

public class PlayerEntity extends AbstractEntity {

    private BufferedImage[][] spriteAnimation; //Create array to hold each subImage of image sprite

    //Variables to control animation
    private int spriteAnimationTick, spriteAnimationIndex, spriteAnmimationSpeed = 25;
    private AnimationConstants PLAYER_ACTION = IDLE; //Animation constant
    private boolean playerMoving = false;
    private boolean playerAttacking = false;
    @Setter
    private boolean movingUp, movingDown, movingLeft, movingRight, jumping; //Control player movement with booleans
    private final float playerSpeed = 1.0f * Game.SCALE;
    private final boolean debugEnabled = true;
    private int[][] currentLevelData;
    private float xOffSet = 21 * Game.SCALE;
    private float yOffSet = 4 * Game.SCALE;

    //Variables to control player jump / gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public PlayerEntity(float xPos, float yPos, int width, int height) {
        super(xPos, yPos, width, height);
        loadSpriteAnimations(); //Takes sprite image and splits it up, turing it into an animation
        initEntityHitbox(xPos, yPos, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
    }

    public void update()
    {
        updatePlayerPosition(); //Updates player pos on screen
        //updateEntityHitbox(); //Updates entity hitBox
        updateSpriteAnimationTick(); //Update sprite animation by looping through index
        updatePlayerActionState(); //Updates player movement state
    }
    public void render(Graphics graphics, int levelOffset)
    {
        //Draws the loaded image on to the screen
        graphics.drawImage(this.spriteAnimation[PLAYER_ACTION.keyCode][this.spriteAnimationIndex],
                (int) (this.entityHitbox.x - this.xOffSet) - levelOffset,
                (int) (this.entityHitbox.y - this.yOffSet),
                width,
                height,
                null
        );
        if (this.debugEnabled) {
            drawEntityHitbox(graphics); //Draws hitbox if debug is enabled
        }
    }

    /**
     * Loops through array, checking when to switch to array index and reset values back to 0.
     */
    private void updateSpriteAnimationTick()
    {
        this.spriteAnimationTick++;
        if (this.spriteAnimationTick >= this.spriteAnmimationSpeed)
        {
            this.spriteAnimationTick = 0;
            this.spriteAnimationIndex++;
            if (this.spriteAnimationIndex >= getSpriteAmount(PLAYER_ACTION)) {
                this.spriteAnimationIndex = 0;
                this.playerAttacking = false;
            }
        }
    }

    private void updatePlayerActionState()
    {

        int playerActionAnimation = PLAYER_ACTION.keyCode; //Starting playerAction animation

        if (this.playerMoving) //Decide wether to mover or keep player in idle
            PLAYER_ACTION = RUNNING;
        else {
            PLAYER_ACTION = IDLE;
        }
        if (this.inAir)
        {
            if (this.airSpeed < 0)
                PLAYER_ACTION = JUMPING;
            else {
                PLAYER_ACTION = FALLING;
            }
        }
        if (this.playerAttacking) //Decide if player should attack
            PLAYER_ACTION = ATTACK;
        if (playerActionAnimation != PLAYER_ACTION.keyCode) //Check if we need to reset animation ticks
        {
            this.spriteAnimationTick = 0;
            this.spriteAnimationIndex = 0;
        }
    }

    private void updatePlayerPosition()
    {

        this.playerMoving = false; //Constant variable to stop player movement

        if (this.jumping)
            setPlayerJump();
        if (!this.inAir)
        {
            if ((!movingLeft && !movingRight) || (movingLeft && movingRight))
                return;
        }
        float xSpeed = 0;
        //Moving the player based on conditions, allows for diagonal movement
        if (this.movingLeft)
            xSpeed -= this.playerSpeed;
        if (this.movingRight)
            xSpeed += this.playerSpeed;

        if (!this.inAir)
        {
            if (!HelperClass.isEntityOnFloor(this.entityHitbox, this.currentLevelData))
                this.inAir = true;
        }

        if (this.inAir)
        {
            if (HelperClass.canEntityPassThrough(this.entityHitbox.x, this.entityHitbox.y + this.airSpeed,
                    this.entityHitbox.width,
                    this.entityHitbox.height, this.currentLevelData)
            ) {
                this.entityHitbox.y += this.airSpeed;
                this.airSpeed += this.gravity;
                updateEntityXPosition(xSpeed);
            } else {
                this.entityHitbox.y = HelperClass.getEntityYPosNextToTiles(this.entityHitbox, this.airSpeed);
                if (this.airSpeed > 0)
                {
                    this.inAir = false;
                    this.airSpeed = 0;
                } else {
                    this.airSpeed = this.fallSpeedAfterCollision;
                }
                updateEntityXPosition(xSpeed);
            }
        } else {
            updateEntityXPosition(xSpeed);
        }
        this.playerMoving = true;
    }

    public void resetPlayerMovement()
    {
        //Reset all player directions
        this.movingUp = false;
        this.movingDown = false;
        this.movingRight = false;
        this.movingLeft = false;
    }

    public void setPlayerJump()
    {
        if (this.inAir)
            return;
        this.inAir = true;
        this.airSpeed = this.jumpSpeed;
    }

    private void updateEntityXPosition(float xSpeed)
    {
        //Checks if player can pass Through area in map
        if (HelperClass.canEntityPassThrough(this.entityHitbox.x + xSpeed, this.entityHitbox.y,
                this.entityHitbox.width,
                this.entityHitbox.height, this.currentLevelData)
        ) {
            this.entityHitbox.x += xSpeed;
        } else {
            this.entityHitbox.x = HelperClass.getEntityXPosNextToWall(this.entityHitbox, xSpeed);
        }
    }

    public void setPlayerAttacking(boolean isAttacking) {this.playerAttacking = isAttacking;}
    public void loadCurrentLevelData(int[][] levelData)
    {
        this.currentLevelData = levelData;
        if (!HelperClass.isEntityOnFloor(this.entityHitbox, levelData))
            this.inAir = true;
    }

    private void loadSpriteAnimations()
    {
        BufferedImage imageSprite = GameFile.getSpriteImage(GameFile.PLAYER_SPRITE); //Load image in

        this.spriteAnimation = new BufferedImage[9][6]; //Initialize array
        for (int o = 0; o < this.spriteAnimation.length; o++) //Stream through Outer layer of the array
        {
            //Stream through Inner layer of the array
            for (int i = 0; i < this.spriteAnimation[o].length; i++)
            {
                //Set sub image to respective index
                this.spriteAnimation[o][i] = imageSprite.getSubimage(i*64, o*40, 64, 40);
            }
        }
    }

}
