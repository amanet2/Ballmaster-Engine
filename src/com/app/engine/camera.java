package com.app.engine;

public class camera implements cameraI {
    private double[] coords = new double[]{0.0, 0.0};
    private double zoom = 1.0;

    public camera() {

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
}

