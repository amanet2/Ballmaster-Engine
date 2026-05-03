package com.app.engine;

import java.awt.Graphics;
import java.util.HashMap;

public interface graphicsSystemI {
    interface gCanvas {
        void clear();
        void render();
    }

    interface gPanel {
        void draw(Graphics g);
        void setCameraTransform(Graphics g, camera c);
        void restoreScaledTransform(Graphics g);
    }

    interface gGraphicsSystem {
        void update();
        int getWindowW();
        void setWindowW(int windowW);
        int getWindowH();
        void setWindowH(int windowH);
//        void setPanel(graphicsSystem.gPanel panel);
        void init(graphicsSystem.gCanvas canvas);
        HashMap<String, Number> getVideoMetrics();
    }
}
