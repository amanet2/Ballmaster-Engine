package com.app.engine;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class spritesImpl implements sprites {
    private Map<String, ImageIcon> base_sprites;
    private HashMap<String, Image> scaled_sprites;

    public spritesImpl() {
        this.base_sprites = new HashMap<>();
        this.scaled_sprites = new HashMap<>();
    }

    public Image getScaledImage(String imagePath, int scaledW, int scaledH) {
        if(imagePath.equalsIgnoreCase("none"))
            return null;

        String spriteKey = String.format("%s%d%d", imagePath, scaledW, scaledH);

        if(this.base_sprites.get(spriteKey) == null)
            this.base_sprites.put(imagePath, new ImageIcon(imagePath));
        if(this.scaled_sprites.get(spriteKey) == null)
            this.scaled_sprites.put(spriteKey, this.base_sprites.get(imagePath).getImage().getScaledInstance(scaledW, scaledH, Image.SCALE_FAST));
        return this.scaled_sprites.get(spriteKey);
    }
}
