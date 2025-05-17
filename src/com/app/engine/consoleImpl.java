package com.app.engine;

import java.util.Arrays;
import java.util.HashMap;

public class consoleImpl implements console {
    private HashMap<String, cmdImpl> commands;

    public consoleImpl() {
        commands = new HashMap<>();
    }

    public String readLine(String line) {
        String[] toks = line.trim().split(" ");
        String cmdToDoRequested = toks[0];
        cmdImpl cmdToDo = commands.get(cmdToDoRequested);
        if(cmdToDo != null) {
            String[] cmdToDoArgs = toks.length > 1 ? Arrays.copyOfRange(toks, 1, toks.length) : new String[]{""};
            return cmdToDo.doCmd(cmdToDoArgs);
        }
        else
            return "";
    }

    public void registerCmd(String cmdKey, cmdImpl cmdToRegister) {
        commands.put(cmdKey, cmdToRegister);
    }
}
