package com.app.engine;

public class engine {
    public consoleSystem consoleSystem;
    public cVarSystem cVarSystem;
    public graphicsSystem graphicsSystem;
    public schedulerSystem schedulerSystem;
    public spriteSystem spriteSystem;

    public engine() {
        this.consoleSystem = new consoleSystem();
        this.cVarSystem = new cVarSystem();
        this.graphicsSystem = new graphicsSystem();
        this.schedulerSystem = new schedulerSystem();
        this.spriteSystem = new spriteSystem();
    }
}
