package org.devlev.utils;

import org.devlev.Game;

public class GameConstants {

    public enum PlayerDirection
    {
        LEFT(0),
        UP(1),
        RIGHT(2),
        DOWN(3);

        public final int keyCode;

        PlayerDirection(int keyCode) {
            this.keyCode = keyCode;
        }

        public static PlayerDirection getPlayerDirection(int keyCode)
        {
            for (PlayerDirection playerDirection : PlayerDirection.values())
            {
                if (playerDirection.keyCode == keyCode)
                    return playerDirection;
            }
            return null;
        }
    }

    public enum AnimationConstants
    {
        IDLE(0),
        RUNNING(1),
        JUMPING(2),
        FALLING(3),
        GROUND(4),
        DAMAGE(5),
        ATTACK(6),
        ATTACK_JUMP_1(7),
        ATTACK_JUMP_2(8);

        public final int keyCode;

        AnimationConstants(int keyCode) {
            this.keyCode = keyCode;
        }

        public static int getSpriteAmount(AnimationConstants animationConstants)
        {
            switch (animationConstants)
            {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case DAMAGE:
                    return 4;
                case JUMPING:
                case ATTACK:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default: return 1;
            }
        }

    }

    public static class UIElements
    {
        public static class MenuButtonUI
        {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtonUI
        {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }
        public static class URMButtonUI
        {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
        }
        public static class VolumeButtonUI
        {
            //Default ui position variables
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            //position variables relative to game scale
            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }

    public static class UXElements
    {
        public static class EnvironmentUX
        {
            //Default cloud size
            public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
            public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
            public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
            public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

            //size relative to game scale
            public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
            public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
            public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
            public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        }
    }

}
