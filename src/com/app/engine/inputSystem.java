package com.app.engine;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class inputSystem implements inputSystemI {
    public static class gMouse implements inputSystemI.gMouse {

    }

    public static class gKeyboard implements inputSystemI.gKeyboard {
        Queue<Integer> inputReleaseQueue;
        Queue<Integer> inputPressQueue;
        boolean shiftMode;
        boolean ctrlMode;

        private HashMap<Integer, String> shiftKeyMap;
        private HashMap<Integer, String> specialKeyMap;
        private HashMap<String, Integer> subKeyMap;

        public gKeyboard() {
            this.inputPressQueue = new LinkedList<>();
            this.inputReleaseQueue = new LinkedList<>();
            this.shiftMode = false;
            this.ctrlMode = false;
            this.shiftKeyMap = new HashMap<>();
            this.shiftKeyMap.put(KeyEvent.VK_SEMICOLON, ":");
            this.shiftKeyMap.put(KeyEvent.VK_MINUS, "_");
            this.shiftKeyMap.put(KeyEvent.VK_EQUALS, "+");
            this.shiftKeyMap.put(KeyEvent.VK_QUOTE, "\"");
            this.shiftKeyMap.put(KeyEvent.VK_COMMA, "<");
            this.shiftKeyMap.put(KeyEvent.VK_PERIOD, ">");
            this.shiftKeyMap.put(KeyEvent.VK_SLASH, "?");
            this.shiftKeyMap.put(KeyEvent.VK_BACK_QUOTE, "~");
            this.shiftKeyMap.put(KeyEvent.VK_1, "!");
            this.shiftKeyMap.put(KeyEvent.VK_2, "@");
            this.shiftKeyMap.put(KeyEvent.VK_3, "#");
            this.shiftKeyMap.put(KeyEvent.VK_4, "$");
            this.shiftKeyMap.put(KeyEvent.VK_5, "%");
            this.shiftKeyMap.put(KeyEvent.VK_6, "^");
            this.shiftKeyMap.put(KeyEvent.VK_7, "&");
            this.shiftKeyMap.put(KeyEvent.VK_8, "*");
            this.shiftKeyMap.put(KeyEvent.VK_9, "(");
            this.shiftKeyMap.put(KeyEvent.VK_0, ")");
            this.specialKeyMap = new HashMap<>();
            this.specialKeyMap.put(KeyEvent.VK_SPACE, " ");
            this.specialKeyMap.put(KeyEvent.VK_SEMICOLON, ";");
            this.specialKeyMap.put(KeyEvent.VK_QUOTE, "'");
            this.specialKeyMap.put(KeyEvent.VK_MINUS, "-");
            this.specialKeyMap.put(KeyEvent.VK_EQUALS, "=");
            this.specialKeyMap.put(KeyEvent.VK_PERIOD, ".");
            this.specialKeyMap.put(KeyEvent.VK_COLON, ":");
            this.specialKeyMap.put(KeyEvent.VK_SLASH, "/");
            this.specialKeyMap.put(KeyEvent.VK_NUM_LOCK, "");
            this.specialKeyMap.put(KeyEvent.VK_PLUS, "+");
            this.specialKeyMap.put(KeyEvent.VK_BACK_SLASH, "\\");
            this.specialKeyMap.put(KeyEvent.VK_SEPARATER, "|");
            this.specialKeyMap.put(KeyEvent.VK_DOWN, "");
            this.specialKeyMap.put(KeyEvent.VK_LEFT, "");
            this.specialKeyMap.put(KeyEvent.VK_RIGHT, "");
            this.specialKeyMap.put(KeyEvent.VK_UP, "");
            this.specialKeyMap.put(KeyEvent.VK_CAPS_LOCK, "");
            this.specialKeyMap.put(KeyEvent.VK_COMMA, ",");
            this.specialKeyMap.put(KeyEvent.VK_BACK_QUOTE, "`");
            this.specialKeyMap.put(KeyEvent.VK_OPEN_BRACKET, "[");
            this.specialKeyMap.put(KeyEvent.VK_CLOSE_BRACKET, "]");
            this.specialKeyMap.put(KeyEvent.VK_TAB, "    ");
            this.specialKeyMap.put(KeyEvent.VK_INSERT, "");
            this.specialKeyMap.put(KeyEvent.VK_HOME, "");
            this.specialKeyMap.put(KeyEvent.VK_PAGE_UP, "");
            this.specialKeyMap.put(KeyEvent.VK_PAGE_DOWN, "");
            this.specialKeyMap.put(KeyEvent.VK_END, "");
            this.specialKeyMap.put(KeyEvent.VK_ALT, "");
            this.subKeyMap = new HashMap<>();
            this.subKeyMap.put("a", KeyEvent.VK_A);
            this.subKeyMap.put("b", KeyEvent.VK_B);
            this.subKeyMap.put("c", KeyEvent.VK_C);
            this.subKeyMap.put("d", KeyEvent.VK_D);
            this.subKeyMap.put("e", KeyEvent.VK_E);
            this.subKeyMap.put("f", KeyEvent.VK_F);
            this.subKeyMap.put("g", KeyEvent.VK_G);
            this.subKeyMap.put("h", KeyEvent.VK_H);
            this.subKeyMap.put("i", KeyEvent.VK_I);
            this.subKeyMap.put("j", KeyEvent.VK_J);
            this.subKeyMap.put("k", KeyEvent.VK_K);
            this.subKeyMap.put("l", KeyEvent.VK_L);
            this.subKeyMap.put("m", KeyEvent.VK_M);
            this.subKeyMap.put("n", KeyEvent.VK_N);
            this.subKeyMap.put("o", KeyEvent.VK_O);
            this.subKeyMap.put("p", KeyEvent.VK_P);
            this.subKeyMap.put("q", KeyEvent.VK_Q);
            this.subKeyMap.put("r", KeyEvent.VK_R);
            this.subKeyMap.put("s", KeyEvent.VK_S);
            this.subKeyMap.put("t", KeyEvent.VK_T);
            this.subKeyMap.put("u", KeyEvent.VK_U);
            this.subKeyMap.put("v", KeyEvent.VK_V);
            this.subKeyMap.put("w", KeyEvent.VK_W);
            this.subKeyMap.put("x", KeyEvent.VK_X);
            this.subKeyMap.put("y", KeyEvent.VK_Y);
            this.subKeyMap.put("z", KeyEvent.VK_Z);
            this.subKeyMap.put("enter", KeyEvent.VK_ENTER);
            this.subKeyMap.put("shift", KeyEvent.VK_SHIFT);
            this.subKeyMap.put("ctrl", KeyEvent.VK_CONTROL);
            this.subKeyMap.put("[", KeyEvent.VK_OPEN_BRACKET);
            this.subKeyMap.put("escape", KeyEvent.VK_ESCAPE);
            this.subKeyMap.put("\\", KeyEvent.VK_BACK_SLASH);
            this.subKeyMap.put("]", KeyEvent.VK_CLOSE_BRACKET);
            this.subKeyMap.put("space", KeyEvent.VK_SPACE);
            this.subKeyMap.put("left", KeyEvent.VK_LEFT);
            this.subKeyMap.put("up", KeyEvent.VK_UP);
            this.subKeyMap.put("right", KeyEvent.VK_RIGHT);
            this.subKeyMap.put("down", KeyEvent.VK_DOWN);
            this.subKeyMap.put("-", KeyEvent.VK_MINUS);
            this.subKeyMap.put("=", KeyEvent.VK_EQUALS);
            this.subKeyMap.put("`", KeyEvent.VK_BACK_QUOTE);
            this.subKeyMap.put("0", KeyEvent.VK_0);
            this.subKeyMap.put("1", KeyEvent.VK_1);
            this.subKeyMap.put("2", KeyEvent.VK_2);
            this.subKeyMap.put("3", KeyEvent.VK_3);
            this.subKeyMap.put("4", KeyEvent.VK_4);
            this.subKeyMap.put("5", KeyEvent.VK_5);
            this.subKeyMap.put("6", KeyEvent.VK_6);
            this.subKeyMap.put("7", KeyEvent.VK_7);
            this.subKeyMap.put("8", KeyEvent.VK_8);
            this.subKeyMap.put("9", KeyEvent.VK_9);
            this.subKeyMap.put("tab", KeyEvent.VK_TAB);
            this.subKeyMap.put(",", KeyEvent.VK_COMMA);
            this.subKeyMap.put(".", KeyEvent.VK_PERIOD);
            this.subKeyMap.put("/", KeyEvent.VK_SLASH);
            this.subKeyMap.put("backspace", KeyEvent.VK_BACK_SPACE);
            this.subKeyMap.put("delete", KeyEvent.VK_DELETE);
        }

        public String getShiftKeyForCode(Integer code) {
            return this.shiftKeyMap.get(code);
        }

        public String getSpecialKeyForCode(Integer code) {
            return this.specialKeyMap.get(code);
        }

        public Integer getCodeForKey(String text) {
            return this.subKeyMap.get(text);
        }

//        // TODO: attach these to game instance where needed
//        public synchronized void keyTyped(KeyEvent e) {
//        }
//
//        public synchronized void keyPressed(KeyEvent e) {
//            this.inputPressQueue.add(e.getKeyCode());
//
//        }
//
//        public synchronized void keyReleased(KeyEvent e) {
//            this.inputReleaseQueue.add(e.getKeyCode());
//        }
    }
}
