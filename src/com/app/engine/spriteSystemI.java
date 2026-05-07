package com.app.engine;

import java.awt.*;

public interface spriteSystemI {
    interface gSprite {
        Image getImage();
        void draw(Graphics g, int x, int y);
    }

    interface gSpriteSystem {
        spriteSystem.gSprite getScaledSprite(String name, int width, int height);
    }
}
