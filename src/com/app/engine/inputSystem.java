package com.app.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class inputSystem {
    public static class gMouse {

    }

    public static class gKeyboard implements KeyListener {
        public gKeyboard() {

        }

        public synchronized void keyTyped(KeyEvent e) {

        }

        public synchronized void keyPressed(KeyEvent e) {

        }

        public synchronized void keyReleased(KeyEvent e) {

        }
    }

    // TODO: map of bindings from string -> binding

    public class binding {
        private int keyCode;
        private consoleSystem.gConsoleCommand command;
        private impulse impulse;
    }

    public class impulse {
        public void keyPressed() {

        }

        public void keyReleased() {

        }
    }
}
