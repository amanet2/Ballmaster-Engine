package com.app.engine;

public interface camera {
    double maxTrackingDistance = 100.0;
    double maxVelocity = 32.0;
    int shakeDuration = 200;
    double accelRate = 0.5;
    double decelRate = 0.1;

    void updatePositionFree(long gameTime, int rateSimulation, int rateShell);

//    void updatePositionTrackCoords(int[] worldCoords);

    void snapToWorldCoords(int[] worldCoords);

    void pointAtWorldCoords(int[] worldCoords);

    void doShakeAtWorldCoords(int[] worldCoords, int rateSimulation, int rateShell);

    void startShake(long gameTime);
}
