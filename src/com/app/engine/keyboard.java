package com.app.engine;

public interface keyboard {
    String getShiftKeyForCode(Integer code);
    String getSpecialKeyForCode(Integer code);
    Integer getCodeForKey(String keyText);
}
