package com.app.engine;

import java.awt.Graphics;
import java.util.HashMap;

public interface graphicsSystemI {
    interface gCanvas {
        void init();
        void clear();
        void render();
    }

    interface gGraphicsSystem {
        void update();
        int getWindowW();
        void setWindowW(int windowW);
        int getWindowH();
        void setWindowH(int windowH);
        void init(graphicsSystem.gCanvas canvas);
        HashMap<String, Number> getVideoMetrics();
        void restoreTransform();
        Graphics getGraphics();
    }
}
