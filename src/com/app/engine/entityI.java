package com.app.engine;

import com.app.engine.graphicsSystem.gSprite;

import java.awt.*;

public interface entityI {
    gSprite getSprite();

    void draw(Graphics g);
}
