package com.app.engine;

public interface console {
    String readLine(String line);
    void registerCmd(String key, cmdImpl cmdToRegister);
}
