package com.app.engine;

public interface consoleSystemI {
    interface gConsoleCommand {
        String doCommand(String[] args);
        String getDescription();
    }

    interface gConsoleSystem {
        String readLine(String line);
        gConsoleCommand getCmd(String name);
        void registerCmd(String name, consoleSystem.gConsoleCommand command);
        String[] listCmds();
    }
}
