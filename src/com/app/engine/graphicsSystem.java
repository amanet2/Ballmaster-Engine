package com.app.engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class graphicsSystem implements graphicsSystemI {
    public static class gPanel extends JPanel implements graphicsSystemI.gPanel {
        private gGraphicsSystem parentGGraphicsSystem;
        private AffineTransform savedTransform;
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            draw(g);

            drawMetrics(g);

            g.dispose();
        }

        public void draw(Graphics g) {
            // to be overriden
            parentGGraphicsSystem.getVideoMetrics();

            this.savedTransform = ((Graphics2D) g).getTransform();

            // center the screen over 0,0
            g.translate((int)((double)parentGGraphicsSystem.width/2.0), (int)((double)parentGGraphicsSystem.height/2.0));

            // scale the world according to screen height
            double scaleFactor = utils.gMath.scaleDoubleToWindowHeight(1.0, parentGGraphicsSystem.internalScale, parentGGraphicsSystem.height);
            ((Graphics2D) g).scale(scaleFactor, scaleFactor);

        }

        public void setCameraTransform(Graphics g, camera c) {
            // move world to match camera coords
            double[] cCoods = c.getCoords();
            g.translate(-(int)cCoods[0], -(int)cCoods[1]);

            //zoom in or out depending on camera setting
            double cameraZoom = c.getZoom();
            ((Graphics2D) g).scale(cameraZoom, cameraZoom);
        }

        public void restoreScaledTransform(Graphics g) {
            ((Graphics2D) g).setTransform(this.savedTransform);

            // scale ui text according to window screen height
            double scaleFactor = utils.gMath.scaleDoubleToWindowHeight(1.0, parentGGraphicsSystem.internalScale, parentGGraphicsSystem.height);
            ((Graphics2D) g).scale(scaleFactor, scaleFactor);
        }

        private void drawMetrics(Graphics g) {
            g.setColor(Color.WHITE);
            int debugInfoY = 0;
            if(engine.showMetricsVideo) {
                g.drawString("Video FPS: " + parentGGraphicsSystem.videoFramesPerSecondMetricSnapshot, 0, debugInfoY + 25);
                g.drawString("Video Frames: " + parentGGraphicsSystem.videoFrames, 0, debugInfoY + 50);
                g.drawString("Video Frametime AVG: " + parentGGraphicsSystem.videoFrametimeMetricSnapshotAvg + "ms", 0, debugInfoY + 75);
                g.drawString("Video Frametime Lowest: " + parentGGraphicsSystem.videoFrametimeMetricSnapshotLowest + "ms", 0, debugInfoY + 100);
                g.drawString("Video Frametime Highest: " + parentGGraphicsSystem.videoFrametimeMetricSnapshotHighest + "ms", 0, debugInfoY + 125);
                debugInfoY += 125;
            }
        }

        public gPanel() {

        }
    }

    public static class gGraphicsSystem implements graphicsSystemI.gGraphicsSystem {
        private JFrame frame;
        private int width;
        private int height;
        private double internalScale = 768.0;

        // longtime to get snapshots for ALL metrics
        private long frameMetricTimeMillis = System.currentTimeMillis() + 1000;

        public int videoFrames = 0;
        private int videoFramesPerSecondMetric = 0;
        public int videoFramesPerSecondMetricSnapshot = 0;
        private double videoFrametime = 0;
        private long videoFrametimeLast = 0;
        private double videoFrametimeMetric = 0;
        private double videoFrametimeMetricLowest = 0;
        public double videoFrametimeMetricSnapshotLowest = 0;
        public double videoFrametimeMetricSnapshotAvg = 0;
        private double videoFrametimeMetricHighest = 0;
        public double videoFrametimeMetricSnapshotHighest = 0;

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        private void getVideoMetrics() {
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

        public void setPanel(gPanel panel) {
            panel.parentGGraphicsSystem = this;
            this.frame = new JFrame("Ballmaster Engine");
            this.frame.setResizable(false);
            this.frame.setBackground(Color.BLACK);
            panel.setBackground(Color.BLACK);
            this.frame.setPreferredSize(new Dimension(this.width, this.height));
            this.frame.setContentPane(panel);
            this.frame.pack();
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
        }

        public void update() {
            this.frame.repaint();
        }
    }
}
