package com.app.engine;

public interface cameraSystemI {
    interface gCamera {
        void setZoom(double zoom);
        double getZoom();

        void setCoords(double[] coords);
        double[] getCoords();

        void setVec(double[] vec);
        double[] getVec();
    }
}
