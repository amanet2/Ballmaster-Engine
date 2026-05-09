package com.app.engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import com.app.engine.fileSystem.gFile;

public class consoleSystem implements consoleSystemI {
    public static class gConsoleCommand implements consoleSystemI.gConsoleCommand {
        private String description;

        public gConsoleCommand() {
            this.description = "";
        }

        public gConsoleCommand(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }

        public String doCommand(String[] args) {
            return "";
        }
    }

    public static class gConsoleSystem implements consoleSystemI.gConsoleSystem {
        private HashMap<String, gConsoleCommand> commands;

        public gConsoleSystem() {
            commands = new HashMap<>();
        }

        public String readLine(String line) {
            String[] toks = line.trim().split(" ");
            String cmdToDoRequested = toks[0];
            gConsoleCommand command = commands.get(cmdToDoRequested);
            if(command == null)
                return "null";
            String[] args = toks.length > 1 ? Arrays.copyOfRange(toks, 1, toks.length) : new String[]{""};
            try {
                return command.doCommand(args);
            }
            catch(Exception ce) {
                ce.printStackTrace();
            }
            return "null";
        }

        public String execCfgFile(gFile file) {
            for(String line : file.getFileLines()) {
                String[] tokens = line.split(" ");

                StringBuilder execLineBuilder = new StringBuilder("");
                for(String token : tokens) {
                    String trimmed = token.trim();

                    if(trimmed.isEmpty()) continue;
                    if(trimmed.startsWith(";") || trimmed.startsWith("#")) break;

                    execLineBuilder.append(" ").append(trimmed);
                }
                String execLine = execLineBuilder.toString().trim();

                if(execLine.isEmpty()) continue;

                System.out.println("% " + execLine);
                System.out.println(this.readLine(execLine));
            }
            return "";
        }

        public void registerCmd(String name, gConsoleCommand command) {
            commands.put(name, command);
        }

        public gConsoleCommand getCmd(String name) {
            return commands.get(name);
        }

        public String[] listCmds() {
            return new TreeSet<>(commands.keySet()).toArray(new String[0]);
        }
    }
}
