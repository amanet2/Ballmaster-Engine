package com.app.engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;

public class graphicsSystem implements graphicsSystemI {
    public static class gPanel extends JPanel implements graphicsSystemI.gPanel {
        private gGraphicsSystem parentGGraphicsSystem;
        private AffineTransform savedTransform;

        public void draw(Graphics g) {
            // to be overriden
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
            int debugInfoY = 50;
            int debugInfoSeparation = 25;
            if(engine.showMetricsVideo) {
                g.drawString("Video FPS: " + parentGGraphicsSystem.videoFramesPerSecondMetricSnapshot, 0, debugInfoY);
                g.drawString("Video Frames: " + parentGGraphicsSystem.videoFrames, 0, debugInfoY  + debugInfoSeparation);
                g.drawString("Video Frametime AVG: " + parentGGraphicsSystem.videoFrametimeMetricSnapshotAvg + "ms", 0, debugInfoY  + debugInfoSeparation*2);
                g.drawString("Video Frametime Lowest: " + parentGGraphicsSystem.videoFrametimeMetricSnapshotLowest + "ms", 0, debugInfoY + debugInfoSeparation*3);
                g.drawString("Video Frametime Highest: " + parentGGraphicsSystem.videoFrametimeMetricSnapshotHighest + "ms", 0, debugInfoY + debugInfoSeparation*4);
            }
        }

        public gPanel() {
            this.setLayout(null);
            this.setIgnoreRepaint(true);
        }
    }

    public static class gGraphicsSystem implements graphicsSystemI.gGraphicsSystem {
        private JFrame frame;
        private int width = 1024;  // defaults
        private int height = 768;  // defaults
        private double internalScale = 768.0;
        private BufferStrategy bufferStrategy;
        private gPanel panel;

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

        public BufferStrategy getDrawStrategy() {
            return this.bufferStrategy;
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
            panel.setBackground(Color.BLACK);
            this.panel = panel;
            this.panel.setPreferredSize(new Dimension(this.width, this.height));

            this.frame = new JFrame("Ballmaster Engine");
            this.frame.setIgnoreRepaint(true);
            this.frame.setResizable(false);
            this.frame.setBackground(Color.BLACK);
            this.frame.setPreferredSize(new Dimension(this.width, this.height));
            this.frame.setBounds(0, 0, this.width, this.height);
            this.frame.setContentPane(panel);
            this.frame.pack();
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setLocationRelativeTo(null);
            this.frame.setVisible(true);
            this.frame.createBufferStrategy(2);

            this.bufferStrategy = this.frame.getBufferStrategy();
        }

        public void update() {
            Graphics2D g = (Graphics2D) this.bufferStrategy.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0,0, this.width, this.height);

            this.panel.draw(g);

            getVideoMetrics();
            this.panel.drawMetrics(g);
        }

        public void finish() {
            Graphics2D g = (Graphics2D) this.bufferStrategy.getDrawGraphics();
            g.dispose();
            this.bufferStrategy.show();
        }
    }
}
