package com.app.engine;

import java.awt.Image;

public interface sprites {
    Image getScaledImage(String imagePath, int scaledW, int scaledH);
}
