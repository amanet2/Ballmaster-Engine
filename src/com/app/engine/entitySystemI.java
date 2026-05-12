package com.app.engine;

import java.awt.Graphics;

import com.app.engine.utils.gBounds;

public interface entitySystemI {
    interface gEntity {
        graphicsSystem.gSprite getSprite();
        gBounds getBounds();
        void setBounds(gBounds bounds);

        void draw(Graphics g);
    }
}
