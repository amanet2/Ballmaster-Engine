package com.app.engine;

import java.awt.Graphics;

public interface graphicsSystemI {
    interface gPanel {
        void draw(Graphics g);
    }

    interface gGraphicsSystem {
        void update();
        int getWidth();
        void setWidth(int width);
        int getHeight();
        void setHeight(int height);
        void setPanel(graphicsSystem.gPanel panel);
    }
}
