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
        void init(graphicsSystem.gCanvas canvas);
        void update();

        Graphics getGraphics();

        void setRenderDims(int[] dims);
        int[] getRenderDims();
        int getRenderW();
        int getRenderH();

        void setWindowDims(int[] dims);
        int[] getWindowDims();
        int getWindowW();
        int getWindowH();

        void setCameraTransform(camera c);

        HashMap<String, Number> getVideoMetrics();
    }
}
