package com.app.engine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import com.app.engine.fileSystem;
import com.app.engine.fileSystem.gFile;

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
        private fileSystem fileSystem;
        private Map<String, gFile> spriteFiles;
        private Map<String, ImageIcon> baseImages;
        private HashMap<String, gSprite> scaledSprites;

        public gSpriteSystem() {
            this.fileSystem = new fileSystem();
            this.spriteFiles = new HashMap<>();
            this.baseImages = new HashMap<>();
            this.scaledSprites = new HashMap<>();
        }

        public gSprite getScaledSprite(String fullPath, int width, int height) {
            if(fullPath.equalsIgnoreCase("none"))
                return null;

            this.spriteFiles.putIfAbsent(fullPath, this.fileSystem. new gFile(fullPath));

            gFile spriteFile = this.spriteFiles.get(fullPath);
            String spriteFileFullpath = spriteFile.getFullPath();

            this.baseImages.putIfAbsent(spriteFileFullpath, new ImageIcon(spriteFileFullpath));

            String name = String.format("%s%d%d", spriteFileFullpath, width, height);

            this.scaledSprites.putIfAbsent(name, new gSprite(this.baseImages.get(spriteFileFullpath).getImage().getScaledInstance(width, height, Image.SCALE_FAST)));

            return this.scaledSprites.get(name);
        }
    }
}
