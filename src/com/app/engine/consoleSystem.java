package com.app.engine;

import java.util.Arrays;
import java.util.HashMap;

public class consoleSystem implements consoleSystemI {
    public class gConsoleCommand implements consoleSystemI.gConsoleCommand {
        public gConsoleCommand() {

        }

        public String doCommand(String[] args) {
            return "";
        }
    }

    public class gConsoleSystem implements consoleSystemI.gConsoleSystem {
        private HashMap<String, gConsoleCommand> commands;

        public gConsoleSystem() {
            commands = new HashMap<>();
        }

        public String readLine(String line) {
            String[] toks = line.trim().split(" ");
            String cmdToDoRequested = toks[0];
            gConsoleCommand command = commands.get(cmdToDoRequested);
            if(command == null)
                return "";
            String[] args = toks.length > 1 ? Arrays.copyOfRange(toks, 1, toks.length) : new String[]{""};
            return command.doCommand(args);
        }

        public void registerCmd(String name, gConsoleCommand command) {
            commands.put(name, command);
        }
    }
}
