package com.app.engine;

import com.app.engine.consoleSystem.gConsoleCommand;
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

        // setups
        registerDefaultCmds();
        registerDefaultCVars();
    }

    private void registerDefaultCmds() {
        // TODO: make gameFiles-related commands (commented below) work at engine level (fix paths n stuff)
        gConsoleCommand gConsoleCommandClear = new gConsoleCommand("clears the console") {
            @Override
            public String doCommand(String[] args) {
                System.out.print("\033\143");
                return "";
            }
        };
        gConsoleCommand gConsoleCommandEcho = new gConsoleCommand("prints text") {
            @Override
            public String doCommand(String[] args) {
                StringBuilder echoStrBuilder = new StringBuilder();
                for(String tok : args) {
                    echoStrBuilder.append(" ").append(tok);
                }
                echoStrBuilder.append("\n");
                System.out.print(echoStrBuilder.substring(1));
                return "";
            }
        };
        gConsoleCommand gConsoleCommandListCmds = new gConsoleCommand("lists commands") {
            @Override
            public String doCommand(String[] args) {
                String[] names = gConsoleSystem.listCmds();
                System.out.println("total " + names.length);
                for(String name : names) {
                    System.out.println(name + " -> " + gConsoleSystem.getCmd(name).getDescription());
                }
                return "";
            }
        };
        gConsoleCommand gConsoleCommandListCVars = new gConsoleCommand("lists Cvars") {
            @Override
            public String doCommand(String[] args) {
                String[] names = gCVarSystem.getCVarList();
                System.out.println("total " + names.length);
                for(String name : names) {
                    System.out.printf("%s = %s%n", name, gCVarSystem.getCVarValue(name));
                }
                return "";
            }
        };
        gConsoleCommand gConsoleCommandQuit = new gConsoleCommand("quits the game") {
            @Override
            public String doCommand(String[] args) {
                System.exit(0);
                return "You will never see this la la la!";
            }
        };
        gConsoleCommand gConsoleCommandSet = new gConsoleCommand("sets a cvar") {
            @Override
            public String doCommand(String[] args) {
                if(args.length < 2)
                    return "Usage: set CVAR_NAME CVAR_VALUE";
                return gCVarSystem.setCVarValue(args[0], args[1]);
            }
        };
        gConsoleCommand gConsoleCommandVstr = new gConsoleCommand("inserts the current value of a cvar as command text") {
            @Override
            public String doCommand(String[] args) {
                if(args.length < 1)
                    return "Usage: vstr CVAR_NAME";
                return gCVarSystem.getCVarValue(args[0]);
            }
        };

        gConsoleSystem.registerCmd("clear", gConsoleCommandClear);
        gConsoleSystem.registerCmd("echo", gConsoleCommandEcho);
//        gConsoleSystem.registerCmd("exec", gConsoleCommandExec);
        gConsoleSystem.registerCmd("exit", gConsoleCommandQuit);
        gConsoleSystem.registerCmd("listCmds", gConsoleCommandListCmds);
        gConsoleSystem.registerCmd("listCVars", gConsoleCommandListCVars);
//        gConsoleSystem.registerCmd("listFilesCfg", gConsoleCommandListFiles);
//        gConsoleSystem.registerCmd("listFilesScripts", gConsoleCommandListFilesScripts);
//        gConsoleSystem.registerCmd("listFilesSprites", gConsoleCommandListSprites);
        gConsoleSystem.registerCmd("quit", gConsoleCommandQuit);
//        gConsoleSystem.registerCmd("script", gConsoleCommandScript);
//        gConsoleSystem.registerCmd("scriptFile", gConsoleCommandScriptFile);
        gConsoleSystem.registerCmd("set", gConsoleCommandSet);
        gConsoleSystem.registerCmd("vstr", gConsoleCommandVstr);
    }

    private void registerDefaultCVars() {
        cVarSystem.gCVar cVarShowFps = new cVarSystem.gCVar(engine.showMetricsVideo ? "1" : "0") {
            @Override
            public void onChange() {
                engine.showMetricsVideo = this.getValue().equalsIgnoreCase("1");
            }
        };
        cVarSystem.gCVar cVarRCustomHeight = new cVarSystem.gCVar(Integer.toString(gGraphicsSystem.getHeight())) {
            @Override
            public void onChange() {
                gGraphicsSystem.setHeight(Integer.parseInt(this.getValue()));
            }
        };

        cVarSystem.gCVar cVarRCustomWidth = new cVarSystem.gCVar(Integer.toString(gGraphicsSystem.getWidth())) {
            @Override
            public void onChange() {
                gGraphicsSystem.setWidth(Integer.parseInt(this.getValue()));
            }
        };

//        gCVarSystem.registerCVar("cam_xy", cVarCamXY);
//        gCVarSystem.registerCVar("cam_zoom", cVarCamZoon);
//        gCVarSystem.registerCVar("com_showcamerainfo", cVarShowCamInfo);
        gCVarSystem.registerCVar("com_showfps", cVarShowFps);
//        gCVarSystem.registerCVar("com_showframeinfo", cVarShowFrameInfo);
//        gCVarSystem.registerCVar("com_showtimeelapsed", cVarComShowTimeElapsed);
//        gCVarSystem.registerCVar("fs_spritespath", cVarFsSpriteFilesPath);
//        gCVarSystem.registerCVar("fs_cfgpath", cVarFsCfgFilesPath);
        gCVarSystem.registerCVar("r_customHeight", cVarRCustomHeight);
        gCVarSystem.registerCVar("r_customWidth", cVarRCustomWidth);
    }
}
