package com.app.engine;

public interface inputSystemI {
    interface gMouse {

    }

    interface gKeyboard {
        String getShiftKeyForCode(Integer code);
        String getSpecialKeyForCode(Integer code);
        Integer getCodeForKey(String keyText);
    }
}
