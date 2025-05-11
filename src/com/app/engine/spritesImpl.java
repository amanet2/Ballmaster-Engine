package com.app.engine;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class spritesImpl implements sprites {
    private Map<String, ImageIcon> base_sprites;
    private HashMap<String, Image> scaled_sprites;

    public spritesImpl() {
        base_sprites = new HashMap<>();
        scaled_sprites = new HashMap<>();
    }

    public Image getScaledImage(String imagePath, int scaledW, int scaledH) {
        if(imagePath.equalsIgnoreCase("none"))
            return null;

        String spriteKey = String.format("%s%d%d", imagePath, scaledW, scaledH);

        if(base_sprites.get(spriteKey) == null)
            base_sprites.put(imagePath, new ImageIcon(imagePath));
        if(scaled_sprites.get(spriteKey) == null)
            scaled_sprites.put(spriteKey, base_sprites.get(imagePath).getImage().getScaledInstance(scaledW, scaledH, Image.SCALE_FAST));
        return scaled_sprites.get(spriteKey);
    }
}
