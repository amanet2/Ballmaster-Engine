package com.app.engine;

public interface keyboardI {
    String getShiftKeyForCode(Integer code);
    String getSpecialKeyForCode(Integer code);
    Integer getCodeForKey(String keyText);
}
