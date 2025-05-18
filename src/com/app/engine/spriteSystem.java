package com.app.engine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class spriteSystem implements spriteSystemI {
    public class gSprite implements spriteSystemI.gSprite {
        private Image image;

        public gSprite(Image image) {
            this.image = image;
        }

        public Image getImage() {
            return this.image;
        }
    }

    public class gSpriteSystem implements spriteSystemI.gSpriteSystem {
        private Map<String, ImageIcon> baseImages;
        private HashMap<String, gSprite> scaledSprites;

        public gSpriteSystem() {
            this.baseImages = new HashMap<>();
            this.scaledSprites = new HashMap<>();
        }

        public gSprite getScaledSprite(String fullPath, int width, int height) {
            if(fullPath.equalsIgnoreCase("none"))
                return null;

            if(this.baseImages.get(fullPath) == null)
                this.baseImages.put(fullPath, new ImageIcon(fullPath));

            String name = String.format("%s%d%d", fullPath, width, height);

            if(this.scaledSprites.get(name) == null)
                this.scaledSprites.put(name, new gSprite(this.baseImages.get(fullPath).getImage().getScaledInstance(width, height, Image.SCALE_FAST)));

            return this.scaledSprites.get(name);
        }
    }
}
