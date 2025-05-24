package com.app.engine;

import com.app.engine.consoleSystem.gConsoleSystem;
import com.app.engine.cVarSystem.gCVarSystem;
import com.app.engine.inputSystem.gKeyboard;
import com.app.engine.inputSystem.gMouse;
import com.app.engine.schedulerSystem.gSchedulerSystem;
import com.app.engine.spriteSystem.gSpriteSystem;

public class engine {
    private static engine instance;

    public static engine instance() {
        if(instance == null)
            instance = new engine();
        return instance;
    }

    //systems
    public gConsoleSystem gConsoleSystem;

    public gCVarSystem gCVarSystem;

    public fileSystem fileSystem;

    // TODO: we want picture-in-picture so maybe a single Frame and Panel is not the best choice
    public graphicsSystem graphicsSystem;

    public inputSystem.gKeyboard gKeyboard;
    public inputSystem.gMouse gMouse;

    public schedulerSystem.gSchedulerSystem gSchedulerSystem;

    public spriteSystem.gSpriteSystem gSpriteSystem;

    //utils
    public utils utils;

    private engine() {
        // singleton
        this.gConsoleSystem = new gConsoleSystem();

        // singleton
        this.gCVarSystem = new gCVarSystem();

        // wrapper for multiple-instance class
        this.fileSystem = new fileSystem();

        // TODO: figure out if this should be singletons or not
        this.graphicsSystem = new graphicsSystem();

        // singleton
        this.gKeyboard = new gKeyboard();
        this.gMouse = new gMouse();

        // singleton
        this.gSchedulerSystem = new gSchedulerSystem();

        // singleton
        this.gSpriteSystem = new gSpriteSystem();

        // wrapper for multiple-instance class
        this.utils = new utils();
    }
}
