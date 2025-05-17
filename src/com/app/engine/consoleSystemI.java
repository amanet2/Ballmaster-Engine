package com.app.engine;

public interface consoleSystemI {
    interface gConsoleCommand {
        String doCommand(String[] args);
    }

    interface gConsoleSystem {
        String readLine(String line);
        void registerCmd(String name, consoleSystem.gConsoleCommand command);
    }
}
