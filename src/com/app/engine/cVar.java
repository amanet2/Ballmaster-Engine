package com.app.engine;

public interface cVar {
    void onChange();

    void onUpdate(); // like onChange, but called everytime a value is set

    String getValue();

    void setValue(String newValue);
}
