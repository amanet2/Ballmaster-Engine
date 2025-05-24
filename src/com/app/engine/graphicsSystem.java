package com.app.engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class graphicsSystem implements graphicsSystemI {
    public class gPanel extends JPanel implements graphicsSystemI.gPanel {
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
            g.dispose();
        }

        public void draw(Graphics g) {
            // to be overriden
        }

        public gPanel() {

        }
    }

    public class gGraphicsSystem implements graphicsSystemI.gGraphicsSystem {
        private JFrame frame;
        private int width;
        private int height;

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

        public gGraphicsSystem(gPanel panel, int width, int height) {
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
