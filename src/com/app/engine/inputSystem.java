package com.app.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class inputSystem {
    public static class gInputSystem {
        public HashMap<Integer, impulse> bindings = new HashMap<>();

        private gKeyboard keyboard;
        private gMouse mouse;

        public void bind(Integer e, impulse i) {
            bindings.put(e, i);
        }

        public void init() {
            keyboard = new gKeyboard();
            mouse = new gMouse();

            keyboard.parentGInputSystem = this;
            mouse.parentGInputSystem = this;
        }

        public gKeyboard getKeyboard() {
            return this.keyboard;
        }

        public gInputSystem() {

        }
    }

    public static class gMouse {
        private gInputSystem parentGInputSystem;

    }

    public static class gKeyboard implements KeyListener {
        private gInputSystem parentGInputSystem;

        public gKeyboard() {

        }

        public synchronized void keyTyped(KeyEvent e) {

        }

        public synchronized void keyPressed(KeyEvent e) {
            impulse i = parentGInputSystem.bindings.get(e.getKeyCode());

            if(i != null) i.keyPressed();
        }

        public synchronized void keyReleased(KeyEvent e) {
            impulse i = parentGInputSystem.bindings.get(e.getKeyCode());

            if(i != null) i.keyReleased();
        }
    }

    public static class impulse {
        public impulse() {

        }

        public void keyPressed() {

        }

        public void keyReleased() {

        }
    }
}
