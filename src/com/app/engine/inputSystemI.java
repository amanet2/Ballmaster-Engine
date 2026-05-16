package com.app.engine;

import java.util.HashMap;

public interface inputSystemI {

    interface gInputSystem {
        void init();
        gKeyboard getKeyboard();
        gMouse getMouse();
        void setBind(Integer e, inputSystem.gImpulse i);
        HashMap<Integer, inputSystem.gImpulse> getBinds();
    }

    interface gKeyboard {

    }

    interface gMouse {
        int[] getCoordinates();
    }

    interface gImpulse {
        void keyPressed();
        void keyReleased();
    }
}
