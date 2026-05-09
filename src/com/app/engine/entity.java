package com.app.engine;

import java.awt.*;
import com.app.engine.graphicsSystem.gSprite;

public class entity implements entityI {
    private gSprite sprite;
    private double[] coords;
    private double[] dims;
    private double[] vec;

    public entity() {

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
                (int) (this.coords[0] - this.dims[0]/2.0),
                (int) (this.coords[1] - this.dims[1]/2.0),
                (int) this.dims[0],
                (int) this.dims[1]
        );
    }

    public double[] getDims() {
        return this.dims;
    }

    public void setDims(double[] dims) {
        this.dims = dims;
    }

    public double[] getCoords() {
        return this.coords;
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

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public double getX() {
        return this.coords[0];
    }

    public double getY() {
        return this.coords[1];
    }

    public double getW() {
        return this.dims[0];
    }

    public double getH() {
        return this.dims[1];
    }

    public void setX(double x) {
        this.coords[0] = x;
    }

    public void setY(double y) {
        this.coords[1] = y;
    }

    public void setW(double w) {
        this.dims[0] = w;
    }

    public void setH(double h) {
        this.dims[1] = h;
    }

}
