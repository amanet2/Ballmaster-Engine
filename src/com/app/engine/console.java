package com.app.engine;

public interface console {
    void registerCmd(String cmdKey, cmdImpl cmdToRegister);
}
