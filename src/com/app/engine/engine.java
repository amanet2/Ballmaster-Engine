package com.app.engine;

import com.app.engine.consoleSystem.gConsoleSystem;
import com.app.engine.cVarSystem.gCVarSystem;
import com.app.engine.graphicsSystem.gGraphicsSystem;
import com.app.engine.inputSystem.gKeyboard;
import com.app.engine.inputSystem.gMouse;
import com.app.engine.schedulerSystem.gSchedulerSystem;
import com.app.engine.spriteSystem.gSpriteSystem;

public class engine {
    public static boolean showMetricsVideo = false;

    private static engine instance;

    public static engine instance() {
        if(instance == null)
            instance = new engine();
        return instance;
    }

    // singleton
    public gConsoleSystem gConsoleSystem;

    // singleton
    public gCVarSystem gCVarSystem;

    // wrapper for multiple-instance class
    public fileSystem fileSystem;

    // TODO: we want picture-in-picture so maybe a single Frame and Panel is not the best choice
    public gGraphicsSystem gGraphicsSystem;

    // singletons
    public gKeyboard gKeyboard;
    public gMouse gMouse;

    // singleton
    public gSchedulerSystem gSchedulerSystem;

    // singleton
    public gSpriteSystem gSpriteSystem;

    // wrapper for multiple-instance class
    public utils utils;

    private engine() {
        this.gConsoleSystem = new gConsoleSystem();

        this.gCVarSystem = new gCVarSystem();

        this.fileSystem = new fileSystem();

        this.gGraphicsSystem = new gGraphicsSystem();

        this.gKeyboard = new gKeyboard();
        this.gMouse = new gMouse();

        this.gSchedulerSystem = new gSchedulerSystem();

        this.gSpriteSystem = new gSpriteSystem();

        this.utils = new utils();
    }
}
