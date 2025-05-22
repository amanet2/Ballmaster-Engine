package com.app.engine;

public class engine {
    private static engine instance;

    public static engine instance() {
        if(instance == null)
            instance = new engine();
        return instance;
    }

    public consoleSystem consoleSystem;
    public cVarSystem cVarSystem;
    public graphicsSystem graphicsSystem;
    public schedulerSystem schedulerSystem;
    public spriteSystem spriteSystem;

    private engine() {
        this.consoleSystem = new consoleSystem();
        this.cVarSystem = new cVarSystem();
        this.graphicsSystem = new graphicsSystem();
        this.schedulerSystem = new schedulerSystem();
        this.spriteSystem = new spriteSystem();
    }
}
