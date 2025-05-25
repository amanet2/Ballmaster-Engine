package com.app.engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class graphicsSystem implements graphicsSystemI {
    public class gPanel extends JPanel implements graphicsSystemI.gPanel {
        private gGraphicsSystem parentGGraphicsSystem;
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
            g.dispose();
        }

        public void draw(Graphics g) {
            // to be overriden
            parentGGraphicsSystem.getVideoMetrics();
        }

        public gPanel() {

        }
    }

    public class gGraphicsSystem implements graphicsSystemI.gGraphicsSystem {
        private JFrame frame;
        private int width;
        private int height;

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

                this.videoFramesPerSecondMetric = 0;

                this.videoFrametimeMetricSnapshotLowest = this.videoFrametimeMetricLowest/1000000;
                this.videoFrametimeMetricSnapshotAvg = this.videoFrametimeMetric/1000000000;
                this.videoFrametimeMetricSnapshotHighest = this.videoFrametimeMetricHighest/1000000;

                this.videoFrametimeMetric = 0;
                this.videoFrametimeMetricLowest = 0;
                this.videoFrametimeMetricHighest = 0;
            }
        }

        public gGraphicsSystem(gPanel panel, int width, int height) {
            panel.parentGGraphicsSystem = this;
            this.width = width;
            this.height = height;
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
