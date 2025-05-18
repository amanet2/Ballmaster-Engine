package com.app.engine;

import java.awt.Image;

public interface spriteSystemI {
    interface gSprite {
        Image getImage();
    }

    interface gSpriteSystem {
        spriteSystem.gSprite getScaledSprite(String name, int width, int height);
    }
}
