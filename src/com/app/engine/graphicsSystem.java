package com.app.engine;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class graphicsSystem implements graphicsSystemI {
    public static class gCanvas extends Canvas implements graphicsSystemI.gCanvas {
        private gGraphicsSystem parentGGraphicsSystem;
        private BufferedImage view;
        private Graphics graphics;
        private AffineTransform savedTransform;

        public void init() {
            view = new BufferedImage(
                    parentGGraphicsSystem.getRenderW(),
                    parentGGraphicsSystem.getRenderH(),
                    BufferedImage.TYPE_INT_RGB
            );
            this.createBufferStrategy(2);
        }

        public Graphics getGraphics() {
            if(graphics == null)
                graphics = view.createGraphics();
            return graphics;
        }

        private void resetGraphics() {
            graphics.dispose();
            graphics = null;
        }

        public void clear() {
            Graphics g = this.getGraphics();
            this.savedTransform = ((Graphics2D) g).getTransform();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, parentGGraphicsSystem.getRenderW(), parentGGraphicsSystem.getRenderH());

            // center the canvas over 0,0
            g.translate(
                    (int)((double)parentGGraphicsSystem.getRenderW() /2.0),
                    (int)((double)parentGGraphicsSystem.getRenderH() /2.0)
            );
            this.scaleToScreen();
        }

        public void render() {
            resetGraphics();

            BufferStrategy bs = this.getBufferStrategy();

            Graphics g = bs.getDrawGraphics();
            g.drawImage(
                    view,
                    0,
                    0,
                    this.parentGGraphicsSystem.getWindowW(),
                    this.parentGGraphicsSystem.getWindowH(),
                    null
            );
            g.dispose();

            bs.show();

            parentGGraphicsSystem.setVideoMetrics();
        }

        private void scaleToScreen() {
            Graphics g = this.getGraphics();

            double scaleFactor = utils.gMath.scaleDoubleToWindowHeight(
                    1.0,
                    parentGGraphicsSystem.internalScale,
                    parentGGraphicsSystem.getRenderH()
            );
            ((Graphics2D) g).scale(scaleFactor, scaleFactor);
        }

        public void setCameraTransform(camera c) {
            Graphics g = this.getGraphics();

            // move world to match camera coords
            double[] cCoords = c.getCoords();
            g.translate(-(int)cCoords[0], -(int)cCoords[1]);

            //zoom in or out depending on camera setting
            double cameraZoom = c.getZoom();
            ((Graphics2D) g).scale(cameraZoom, cameraZoom);
        }

        private void restoreTransform() {
            Graphics g = this.getGraphics();

            ((Graphics2D) g).setTransform(this.savedTransform);
            this.scaleToScreen();
        }
    }

    public static class gGraphicsSystem implements graphicsSystemI.gGraphicsSystem {
        private JFrame frame;
        private gCanvas canvas;

        private boolean fullscreen = false;
        private int[] renderDims = { 640, 480 };
        private int[] windowDims = { 640, 480 };
        private double internalScale = 480.0;

        private long frameMetricTimeMillis = System.currentTimeMillis() + 1000;
        private int videoFrames = 0;
        private int videoFramesPerSecondMetric = 0;
        private int videoFramesPerSecondMetricSnapshot = 0;
        private double videoFrametime = 0;
        private long videoFrametimeLast = 0;
        private double videoFrametimeMetric = 0;
        private double videoFrametimeMetricLowest = 0;
        private double videoFrametimeMetricSnapshotLowest = 0;
        private double videoFrametimeMetricSnapshotAvg = 0;
        private double videoFrametimeMetricHighest = 0;
        private double videoFrametimeMetricSnapshotHighest = 0;

        public boolean getFullscreen() {
            return this.fullscreen;
        }

        public void setFullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
        }

        public int[] getRenderDims() {
            return this.renderDims;
        }

        public void setRenderDims(int[] dims) {
            this.renderDims = dims;
        }

        public int getRenderW() {
            return this.renderDims[0];
        }

        public int getRenderH() {
            return this.renderDims[1];
        }

        public int[] getWindowDims() {
            return this.windowDims;
        }

        public void setWindowDims(int[] dims) {
            this.windowDims = dims;
        }

        public int getWindowW() {
            return this.windowDims[0];
        }

        public int getWindowH() {
            return this.windowDims[1];
        }

        public HashMap<String, Number> getVideoMetrics() {
            return new HashMap<>(
                    Map.of(
                            "videoRenderW", this.getRenderW(),
                            "videoRenderH", this.getRenderH(),
                            "videoWindowW", this.getWindowW(),
                            "videoWindowH", this.getWindowH(),
                            "videoFramesPerSecondMetricSnapshot", videoFramesPerSecondMetricSnapshot,
                            "videoFrames", videoFrames,
                            "videoFrametimeMetricSnapshotAvg", videoFrametimeMetricSnapshotAvg,
                            "videoFrametimeMetricSnapshotLowest", videoFrametimeMetricSnapshotLowest,
                            "videoFrametimeMetricSnapshotHighest", videoFrametimeMetricSnapshotHighest
                    )
            );
        }

        private void setVideoMetrics() {
            long currentTimeNanos = System.nanoTime();  // TODO: Use this for video frametime measurements
            long currentTimeMillis = System.currentTimeMillis();

            this.videoFramesPerSecondMetric++;
            this.videoFrames++;

            if(this.videoFrames >= Integer.MAX_VALUE - 1000)
                this.videoFrames = 0;

            this.videoFrametime = currentTimeNanos - this.videoFrametimeLast;
            this.videoFrametimeLast = currentTimeNanos;
            this.videoFrametimeMetric += this.videoFrametime;

            if(this.videoFrametime > this.videoFrametimeMetricHighest)
                this.videoFrametimeMetricHighest = this.videoFrametime;
            if(this.videoFrametime < this.videoFrametimeMetricLowest)
                this.videoFrametimeMetricLowest = this.videoFrametime;

            if(currentTimeMillis > this.frameMetricTimeMillis) {
                this.frameMetricTimeMillis = currentTimeMillis + 1000;

                this.videoFramesPerSecondMetricSnapshot = this.videoFramesPerSecondMetric;


                this.videoFrametimeMetricSnapshotLowest = this.videoFrametimeMetricLowest/1000000;
                this.videoFrametimeMetricSnapshotAvg = this.videoFrametimeMetric/this.videoFramesPerSecondMetric/1000000;
                this.videoFrametimeMetricSnapshotHighest = this.videoFrametimeMetricHighest/1000000;

                this.videoFramesPerSecondMetric = 0;
                this.videoFrametimeMetric = 0;
                this.videoFrametimeMetricLowest = 0;
                this.videoFrametimeMetricHighest = 0;
            }
        }

        public gGraphicsSystem() {

        }

        public void init(gCanvas canvas) {
            this.canvas = canvas;
            canvas.parentGGraphicsSystem = this;

            this.frame = new JFrame("Ballmaster Engine");
            this.frame.setLayout(new BorderLayout());
            this.frame.add(canvas, BorderLayout.CENTER);
            this.frame.setResizable(false);
            this.frame.setBackground(Color.BLACK);

            if(this.fullscreen) {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                gd.setFullScreenWindow(this.frame);
                Rectangle r = gd.getDefaultConfiguration().getBounds();
                this.setWindowDims(new int[]{ r.width, r.height });
            }

            this.frame.setSize(new Dimension(this.getWindowW(), this.getWindowH()));
            canvas.setSize(new Dimension(this.getRenderW(), this.getRenderH()));

            this.frame.add(canvas);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);

            canvas.init();
        }

        public void update() {
            this.canvas.render();
        }

        public void setCameraTransform(camera c) {
            this.canvas.setCameraTransform(c);
        }

        public void restoreTransform() {
            this.canvas.restoreTransform();
        }

        public Graphics getGraphics() {
            return this.canvas.getGraphics();
        }
    }
}
