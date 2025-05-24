package com.app.engine;

public class camera implements cameraI {
    private boolean isShaking = false;
    private long shakeTick = 0;
    private long accelTick = 0;
    private double[] coords = new double[]{0.0, 0.0};
    private boolean[] moves = new boolean[]{false, false, false, false};
    private double[] vels = new double[]{0.0, 0.0, 0.0, 0.0};
    private double forwardVector = 0.0;
    private double zoom = 1.0;

    public camera() {

    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public double getZoom() {
        return zoom;
    }

    public double[] getCoords() {
        return this.coords;
    }

    public void updatePositionFree(long gameTime, int rateSimulation, int rateShell) {
        double mod = (double) rateSimulation / (double) rateShell;
        if (this.accelTick < gameTime) {
            this.accelTick = gameTime;
            this.vels[0] = this.moves[0] ? Math.min(cameraI.maxVelocity, this.vels[0] + cameraI.accelRate) : Math.max(0, this.vels[0] - cameraI.decelRate);
            this.vels[1] = this.moves[1] ? Math.min(cameraI.maxVelocity, this.vels[1] + cameraI.accelRate) : Math.max(0, this.vels[1] - cameraI.decelRate);
            this.vels[2] = this.moves[2] ? Math.min(cameraI.maxVelocity, this.vels[2] + cameraI.accelRate) : Math.max(0, this.vels[2] - cameraI.decelRate);
            this.vels[3] = this.moves[3] ? Math.min(cameraI.maxVelocity, this.vels[3] + cameraI.accelRate) : Math.max(0, this.vels[3] - cameraI.decelRate);
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
        this.shakeTick = gameTime + cameraI.shakeDuration;
    }
}
