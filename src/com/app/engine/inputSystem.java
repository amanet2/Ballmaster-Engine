package com.app.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class inputSystem {
    public static class gInputSystem implements inputSystemI.gInputSystem {
        private gKeyboard keyboard;
        private gMouse mouse;
        private HashMap<Integer, gImpulse> binds = new HashMap<>();

        public void init() {
            keyboard = new gKeyboard();
            mouse = new gMouse();

            keyboard.parentGInputSystem = this;
            mouse.parentGInputSystem = this;
        }

        public void setBind(Integer e, gImpulse i) {
            binds.put(e, i);
        }

        public HashMap<Integer, gImpulse> getBinds() {
            return this.binds;
        }

        public gKeyboard getKeyboard() {
            return this.keyboard;
        }

        public gMouse getMouse() {
            return this.mouse;
        }

        public gInputSystem() {

        }
    }

    public static class gKeyboard implements inputSystemI.gKeyboard, KeyListener {
        private gInputSystem parentGInputSystem;

        public gKeyboard() {

        }

        public synchronized void keyTyped(KeyEvent e) {

        }

        public synchronized void keyPressed(KeyEvent e) {
            gImpulse i = parentGInputSystem.binds.get(e.getKeyCode());

            if(i != null) i.keyPressed();
        }

        public synchronized void keyReleased(KeyEvent e) {
            gImpulse i = parentGInputSystem.binds.get(e.getKeyCode());

            if(i != null) i.keyReleased();
        }
    }

    public static class gMouse implements inputSystemI.gMouse {
        private gInputSystem parentGInputSystem;

    }

    public static class gImpulse implements inputSystemI.gImpulse {
        public gImpulse() {

        }

        public void keyPressed() {

        }

        public void keyReleased() {

        }
    }
}
