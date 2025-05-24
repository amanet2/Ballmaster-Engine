package com.app.engine;

public class engine {
    private static engine instance;

    public static engine instance() {
        if(instance == null)
            instance = new engine();
        return instance;
    }

    //systems
    public consoleSystem.gConsoleSystem gConsoleSystem;

    public cVarSystem.gCVarSystem gCVarSystem;

    public fileSystem fileSystem;

    // TODO: investigate singletons for these systems
    // TODO: also we want picture-in-picture so maybe a single gPanel and camera is not the best engine
    public graphicsSystem graphicsSystem;

    public inputSystem.gKeyboard gKeyboard;
    public inputSystem.gMouse gMouse;

    public schedulerSystem.gSchedulerSystem gSchedulerSystem;

    public spriteSystem.gSpriteSystem gSpriteSystem;

    //utils
    public utils utils;

    private engine() {
        // singleton
        this.gConsoleSystem = new consoleSystem().new gConsoleSystem();

        // singleton
        this.gCVarSystem = new cVarSystem().new gCVarSystem();

        // wrapper for multiple-instance class
        this.fileSystem = new fileSystem();

        // TODO: figure out if these should be singletons or not
        this.graphicsSystem = new graphicsSystem();

        // singleton
        inputSystem inputSystemWrap = new inputSystem();
        this.gKeyboard = inputSystemWrap.new gKeyboard();
        this.gMouse = inputSystemWrap.new gMouse();

        // singleton
        this.gSchedulerSystem = new schedulerSystem().new gSchedulerSystem();

        // singleton
        this.gSpriteSystem = new spriteSystem().new gSpriteSystem();

        // wrapper for multiple-instance class
        this.utils = new utils();
    }
}
