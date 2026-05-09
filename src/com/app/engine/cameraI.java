package com.app.engine;

public interface cameraI {
    double[] getCoords();
    void setCoords(double[] coords);

    double[] getVec();
    void setVec(double[] vec);

    double getZoom();
    void setZoom(double zoom);
}
