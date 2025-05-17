package com.app.engine;

public interface consoleI {
    String readLine(String line);
    void registerCmd(String key, cmd cmdToRegister);
}
