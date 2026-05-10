package com.app.engine;

import java.awt.Graphics;

import com.app.engine.graphicsSystem.gSprite;
import com.app.engine.utils.gBounds;

public interface entityI {
    gSprite getSprite();
    gBounds getBounds();
    void setBounds(gBounds bounds);

    void draw(Graphics g);
}
