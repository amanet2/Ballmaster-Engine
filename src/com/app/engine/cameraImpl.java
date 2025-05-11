package com.app.engine;

public class cameraImpl implements camera {
    private boolean isShaking;
    private long shakeTick;
    private long accelTick;
    private double[] coords;
    private boolean[] moves;
    private double[] vels;
    private double forwardVector;

    public cameraImpl() {
        this.isShaking = false;
        this.shakeTick = 0;
        this.accelTick = 0;
        this.coords = new double[]{0.0, 0.0};
        this.vels = new double[]{0.0, 0.0, 0.0, 0.0};
        this.moves = new boolean[]{false, false, false, false};
        this.forwardVector = 0.0;
    }

    public double[] getCoords() {
        return this.coords;
    }

    public void updatePositionFree(long gameTime, int rateSimulation, int rateShell) {
        double mod = (double) rateSimulation / (double) rateShell;
        if (this.accelTick < gameTime) {
            this.accelTick = gameTime;
            vels[0] = moves[0] ? Math.min(maxVelocity, vels[0] + accelRate) : Math.max(0, vels[0] - decelRate);
            vels[1] = moves[1] ? Math.min(maxVelocity, vels[1] + accelRate) : Math.max(0, vels[1] - decelRate);
            vels[2] = moves[2] ? Math.min(maxVelocity, vels[2] + accelRate) : Math.max(0, vels[2] - decelRate);
            vels[3] = moves[3] ? Math.min(maxVelocity, vels[3] + accelRate) : Math.max(0, vels[3] - decelRate);
        }
        coords[0] += (vels[3] - vels[2]) * mod;
        coords[1] += (vels[1] - vels[0]) * mod;
    }

//    public void updatePositionTrackCoords(int[] worldCoords) {
//
//    }

    public void snapToWorldCoords(int[] worldCoords) {
        coords[0] = worldCoords[0];
        coords[1] = worldCoords[1];
    }


    public void pointAtWorldCoords(int[] worldCoords) {
        // TODO: if we need to do scaling, how should we go about it?
//        double dx = worldCoords[0] - utilsImpl.unscaleInt(screenDims[0] / 2, settings.nativeScale, screenDims[1]) - this.coords[0];
//        double dy = worldCoords[1] - utilsImpl.unscaleInt(screenDims[1] / 2, settings.nativeScale, screenDims[1]) - this.coords[1];
        double newFV = Math.atan2(worldCoords[1] - this.coords[1], worldCoords[0] - this.coords[0]);  // rise over run
        this.forwardVector = newFV < 0.0 ? newFV + Math.PI*2 : newFV;
    }

    public void doShakeAtWorldCoords(int[] worldCoords, int rateSimulation, int rateShell) {
        double mod = (double) rateSimulation / (double) rateShell;
        this.pointAtWorldCoords(worldCoords);
        this.coords[0] = worldCoords[0] - (16.0)*mod*Math.cos(this.forwardVector+Math.PI/2);
        this.coords[1] = worldCoords[1] - (16.0)*mod*Math.sin(this.forwardVector+Math.PI/2);
    }

    public void startShake(long gameTime) {
        this.isShaking = true;
        this.shakeTick = gameTime + shakeDuration;
    }
}
