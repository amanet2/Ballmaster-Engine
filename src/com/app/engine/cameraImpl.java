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
            this.vels[0] = this.moves[0] ? Math.min(camera.maxVelocity, this.vels[0] + camera.accelRate) : Math.max(0, this.vels[0] - camera.decelRate);
            this.vels[1] = this.moves[1] ? Math.min(camera.maxVelocity, this.vels[1] + camera.accelRate) : Math.max(0, this.vels[1] - camera.decelRate);
            this.vels[2] = this.moves[2] ? Math.min(camera.maxVelocity, this.vels[2] + camera.accelRate) : Math.max(0, this.vels[2] - camera.decelRate);
            this.vels[3] = this.moves[3] ? Math.min(camera.maxVelocity, this.vels[3] + camera.accelRate) : Math.max(0, this.vels[3] - camera.decelRate);
        }
        this.coords[0] += (this.vels[3] - this.vels[2]) * mod;
        this.coords[1] += (this.vels[1] - this.vels[0]) * mod;
    }

//    public void updatePositionTrackCoords(int[] worldCoords) {
//
//    }

    public void snapToWorldCoords(int[] worldCoords) {
        this.coords[0] = worldCoords[0];
        this.coords[1] = worldCoords[1];
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
        this.shakeTick = gameTime + camera.shakeDuration;
    }
}
