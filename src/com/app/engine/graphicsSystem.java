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
        public AffineTransform savedTransform;

        public void init() {
            view = new BufferedImage(
                    parentGGraphicsSystem.renderW,
                    parentGGraphicsSystem.renderH,
                    BufferedImage.TYPE_INT_RGB
            );
            this.createBufferStrategy(2);
        }

        public Graphics getGraphics() {
            if(graphics == null)
                graphics = view.getGraphics();
            return graphics;
        }

        public Graphics resetGraphics() {
            graphics = view.getGraphics();
            return graphics;
        }

        public void clear() {
            Graphics g = this.getGraphics();
            this.savedTransform = ((Graphics2D) g).getTransform();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, parentGGraphicsSystem.renderW, parentGGraphicsSystem.renderH);

            // center the canvas over 0,0
            g.translate((int)((double)parentGGraphicsSystem.renderW /2.0), (int)((double)parentGGraphicsSystem.renderH /2.0));
            this.scaleToScreen();
        }

        public void render() {
            this.getGraphics().dispose();
            this.graphics = null;

            BufferStrategy bs = this.getBufferStrategy();

            Graphics g = bs.getDrawGraphics();
            g.drawImage(view, 0, 0, this.parentGGraphicsSystem.windowW, this.parentGGraphicsSystem.windowH, null);
            g.dispose();

            bs.show();

            parentGGraphicsSystem.setVideoMetrics();
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

        public void scaleToScreen() {
            Graphics g = this.getGraphics();

            double scaleFactor = utils.gMath.scaleDoubleToWindowHeight(1.0, parentGGraphicsSystem.internalScale, parentGGraphicsSystem.renderH);
            ((Graphics2D) g).scale(scaleFactor, scaleFactor);
        }

        public void restoreScaledTransform() {
            Graphics g = this.getGraphics();

            ((Graphics2D) g).setTransform(this.savedTransform);
            this.scaleToScreen();
        }
    }

    public static class gGraphicsSystem implements graphicsSystemI.gGraphicsSystem {
        private JFrame frame;
        public gCanvas canvas;
        public boolean fullscreen = false;
        private int windowW = 640;  // defaults
        private int windowH = 480;  // defaults
        public int renderW = 640;
        public int renderH = 480;
        private double internalScale = 480.0;

        // longtime to get snapshots for ALL metrics
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

        public int getWindowW() {
            return this.windowW;
        }

        public void setWindowW(int windowW) {
            this.windowW = windowW;
        }

        public int getWindowH() {
            return this.windowH;
        }

        public void setWindowH(int windowH) {
            this.windowH = windowH;
        }

        public HashMap<String, Number> getVideoMetrics() {
            return new HashMap<>(
                    Map.of(
                            "videoRenderW", this.renderW,
                            "videoRenderH", this.renderH,
                            "videoWindowW", this.windowW,
                            "videoWindowH", this.windowH,
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
                this.windowW = r.width;
                this.windowH = r.height;
            }

            this.frame.setSize(new Dimension(this.windowW, this.windowH));
            canvas.setSize(new Dimension(renderW, renderH));

            this.frame.add(canvas);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);

            canvas.init();
        }

        public void update() {
            this.canvas.render();
        }
    }
}
