package com.app.engine;

public class cameraSystem {
    public static class gCamera implements cameraSystemI.gCamera {
        private double[] coords;
        private double[] vec;
        private double zoom;

        public gCamera() {
            this.zoom = 1.0;
            this.coords = new double[]{0.0, 0.0};
            this.vec = new double[]{ 0.0, 0.0 };
        }

        public void setZoom(double zoom) {
            this.zoom = zoom;
        }

        public double getZoom() {
            return zoom;
        }

        public void setCoords(double[] coords) {
            this.coords = coords;
        }

        public double[] getCoords() {
            return this.coords;
        }

        public void setVec(double[] vec) {
            this.vec = vec;
        }

        public double[] getVec() {
            return this.vec;
        }
    }
}
