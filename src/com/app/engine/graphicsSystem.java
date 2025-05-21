package com.app.engine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class graphicsSystem implements graphicsSystemI {
    public class gPanel extends JPanel {
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

    public class gGraphicsSystem {
        private JFrame frame;

        public gGraphicsSystem(gPanel panel) {
            this.frame = new JFrame("Ballmaster Engine");
            this.frame.setResizable(false);
            this.frame.setBackground(Color.BLACK);
            panel.setBackground(Color.BLACK);
            this.frame.setPreferredSize(new Dimension(settings.width,settings.height));
            this.frame.setContentPane(panel);
            this.frame.pack();
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setVisible(true);
        }

        public void update() {
            this.frame.repaint();
        }
    }
}
