package com.app.engine;

import java.awt.*;

public class entitySystem {
    public static class gEntity implements entitySystemI.gEntity {
        private graphicsSystem.gSprite sprite;
        private utils.gBounds bounds;
        private double[] vec;

        public gEntity() {

        }

        public void setBounds(utils.gBounds bounds) {
            this.bounds = bounds;
        }

        public utils.gBounds getBounds() {
            return this.bounds;
        }

        public graphicsSystem.gSprite getSprite() {
            return this.sprite;
        }

        public void setSprite(graphicsSystem.gSprite sprite) {
            this.sprite = sprite;
        }

        public void draw(Graphics g) {
            this.getSprite().draw(
                    g,
                    (int) (this.getBounds().getX()),
                    (int) (this.getBounds().getY()),
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
}
