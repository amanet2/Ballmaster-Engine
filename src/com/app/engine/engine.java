package com.app.engine;

public class engine {
    private static engine instance;

    public static engine instance() {
        if(instance == null)
            instance = new engine();
        return instance;
    }

    //systems
    private consoleSystem consoleSystem;
    public consoleSystem.gConsoleSystem gConsoleSystem;

    private cVarSystem cVarSystem;
    public cVarSystem.gCVarSystem gCVarSystem;

    public fileSystem fileSystem;

    // TODO: investigate singletons for these systems
    // TODO: also we want picture-in-picture so maybe a single gPanel and camera is not the best engine
    public graphicsSystem graphicsSystem;
    public inputSystem inputSystem;
    public schedulerSystem schedulerSystem;

    private spriteSystem spriteSystem;
    public spriteSystem.gSpriteSystem gSpriteSystem;

    //utils
    public utils utils;

    private engine() {
        // singleton
        this.consoleSystem = new consoleSystem();
        this.gConsoleSystem = this.consoleSystem.new gConsoleSystem();

        // singleton
        this.cVarSystem = new cVarSystem();
        this.gCVarSystem = this.cVarSystem.new gCVarSystem();

        // wrapper for multiple-instance class
        this.fileSystem = new fileSystem();

        // TODO: figure out if these should be singletons or not
        this.graphicsSystem = new graphicsSystem();
        this.inputSystem = new inputSystem();
        this.schedulerSystem = new schedulerSystem();

        // singleton
        this.spriteSystem = new spriteSystem();
        this.gSpriteSystem = this.spriteSystem.new gSpriteSystem();

        // wrapper for multiple-instance class
        this.utils = new utils();
    }
}
