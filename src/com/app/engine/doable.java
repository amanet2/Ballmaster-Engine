package com.app.engine;

public interface doable {
    void doCommand();

    boolean checkValue();

    void doCommandAsServerFromClient(String clientId, String command);

    String doCommand(String command);

    String undoCommand(String command);
}
