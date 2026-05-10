package com.app.engine;

import java.awt.Graphics;

import com.app.engine.graphicsSystem.gSprite;
import com.app.engine.utils.gBounds;

public class entity implements entityI {
    private gSprite sprite;
    private gBounds bounds;
    private double[] vec;

    public entity() {

    }

    public void setBounds(gBounds bounds) {
        this.bounds = bounds;
    }

    public gBounds getBounds() {
        return this.bounds;
    }

    public gSprite getSprite() {
        return this.sprite;
    }

    public void setSprite(gSprite sprite) {
        this.sprite = sprite;
    }

    public void draw(Graphics g) {
        this.getSprite().draw(
                g,
                (int) (this.getBounds().getX() - this.getBounds().getWidth()/2.0),
                (int) (this.getBounds().getY() - this.getBounds().getHeight()/2.0),
                (int) this.getBounds().getWidth(),
                (int) this.getBounds().getHeight()
        );
    }

    public double[] getVec() {
        return this.vec;
    }

    public void setVec(double[] vec) {
        this.vec = vec;
    }

    public double getDx() {
        return this.vec[0];
    }

    public void setDx(double dx) {
        this.vec[0] = dx;
    }

    public double getDy() {
        return this.vec[1];
    }

    public void setDy(double dy) {
        this.vec[1] = dy;
    }
}
