package com.app.engine;

import javax.swing.ImageIcon;
import java.awt.Image;
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

        public gSprite getScaledSprite(String path, int width, int height) {
            if(path.equalsIgnoreCase("none"))
                return null;

            this.baseImages.putIfAbsent(path, new ImageIcon(path));

            String name = String.format("%s%d%d", path, width, height);

            this.scaledSprites.putIfAbsent(name, new gSprite(this.baseImages.get(path).getImage().getScaledInstance(width, height, Image.SCALE_FAST)));

            return this.scaledSprites.get(name);
        }
    }
}
