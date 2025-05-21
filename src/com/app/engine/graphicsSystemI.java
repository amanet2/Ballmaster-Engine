package com.app.engine;

import java.awt.Graphics;

public interface graphicsSystemI {
    interface gPanel {
        void draw(Graphics g);
    }

    interface gGraphicsSystem {
        void update();
    }
}
