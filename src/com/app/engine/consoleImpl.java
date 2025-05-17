package com.app.engine;

import java.util.HashMap;

public class consoleImpl implements console {
    private HashMap<String, cmd> commands;

    public consoleImpl() {
        commands = new HashMap<>();
    }

    public void read(String cmdString) {

    }

    public void registerCmd(String cmdKey, cmdImpl cmdToRegister) {
        commands.put(cmdKey, cmdToRegister);
    }
}
