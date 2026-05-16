package com.app.engine;

import java.awt.*;

import com.app.engine.cameraSystem.gCamera;

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

        int[] getWindowXY();
        void setWindowDims(int[] dims);
        int[] getWindowDims();
        int getWindowW();
        int getWindowH();

        void setCameraTransform(gCamera c, boolean resetTransform);

        String[] getVideoMetrics();
    }

    interface gSprite {
        Image getImage();
        void draw(Graphics g, int x, int y);
    }

    interface gSpriteSystem {
        gSprite getSprite(String name);
    }

    interface gTexture {
        TexturePaint getTexturePaint();
    }

    interface gTextureSystem {
        gTexture getTexture(String name);
    }
}
