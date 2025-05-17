package com.app.engine;

import java.util.Arrays;
import java.util.HashMap;

public class console implements consoleI {
    private HashMap<String, cmd> commands;

    public console() {
        commands = new HashMap<>();
    }

    public String readLine(String line) {
        String[] toks = line.trim().split(" ");
        String cmdToDoRequested = toks[0];
        cmd cmdToDo = commands.get(cmdToDoRequested);
        if(cmdToDo != null) {
            String[] cmdToDoArgs = toks.length > 1 ? Arrays.copyOfRange(toks, 1, toks.length) : new String[]{""};
            return cmdToDo.doCmd(cmdToDoArgs);
        }
        else
            return "";
    }

    public void registerCmd(String cmdKey, cmd cmdToRegister) {
        commands.put(cmdKey, cmdToRegister);
    }
}
