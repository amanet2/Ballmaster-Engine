package com.app.engine;

import com.app.engine.consoleSystem.gConsoleCommand;
import com.app.engine.consoleSystem.gConsoleSystem;
import com.app.engine.cVarSystem.gCVarSystem;
import com.app.engine.graphicsSystem.gGraphicsSystem;
import com.app.engine.schedulerSystem.gSchedulerSystem;
import com.app.engine.fileSystem.gBaseFileSystem;
import com.app.engine.fileSystem.gFile;
import com.app.engine.inputSystem.gInputSystem;

import java.util.Arrays;

public class engine {
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
    public gBaseFileSystem gBaseFileSystem;
    public gInputSystem gInputSystem;
    public gGraphicsSystem gGraphicsSystem;

    // singleton
    public gSchedulerSystem gSchedulerSystem;

    // wrapper for multiple-instance class
    public utils utils;

    private engine() {
        this.gConsoleSystem = new gConsoleSystem();

        this.gCVarSystem = new gCVarSystem();

        this.gBaseFileSystem = new gBaseFileSystem();

        this.gGraphicsSystem = new gGraphicsSystem();

        this.gSchedulerSystem = new gSchedulerSystem();

        this.gInputSystem = new gInputSystem();

        this.utils = new utils();

        // setups
        registerDefaultCmds();
        registerDefaultCVars();
    }

    private void registerDefaultCmds() {
        gConsoleCommand gConsoleCommandClear = new gConsoleCommand("clears the console") {
            @Override
            public String doCommand(String[] args) {
                System.out.print("\033\143");
                return "";
            }
        };
        gConsoleCommand gConsoleCommandExec = new gConsoleCommand("execute cfg file") {
            @Override
            public String doCommand(String[] args) {
                if(args.length < 1 || args[0].trim().isEmpty())
                    return "For executing a cfg file. Usage: exec CFG_FILE";
                String path = args[0].trim();
                gFile file = gBaseFileSystem.getFileSystemConfig().getRootDirectory().getFile(path);
                if (file == null)
                    return "null";
                return gConsoleSystem.execCfgFile(file);
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
                return gCVarSystem.setCVarValue(args[0], args[1], false);
            }
        };
        gConsoleCommand gConsoleCommandSetArchive = new gConsoleCommand("sets a cvar and saves to cfg") {
            @Override
            public String doCommand(String[] args) {
                if(args.length < 2)
                    return "Usage: set CVAR_NAME CVAR_VALUE";
                return gCVarSystem.setCVarValue(args[0], args[1], true);
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
        gConsoleCommand gConsoleCommandListFilesCfg = new gConsoleCommand("lists cfg files") {
            @Override
            public String doCommand(String[] args) {
                return "coming soon";
            }
        };
        gConsoleCommand gConsoleCommandListSprites = new gConsoleCommand("lists sprite files") {
            @Override
            public String doCommand(String[] args) {
                return "coming soon";
            }
        };
        gConsoleCommand gConsoleCommandScript = new gConsoleCommand("executes a line of script") {
            @Override
            public String doCommand(String[] args) {
                return "coming soon";
            }
        };
        gConsoleCommand gConsoleCommandScriptFile = new gConsoleCommand("executes a script file") {
            @Override
            public String doCommand(String[] args) {
                return "coming soon";
            }
        };

        gConsoleSystem.registerCmd("clear", gConsoleCommandClear);
        gConsoleSystem.registerCmd("echo", gConsoleCommandEcho);
        gConsoleSystem.registerCmd("exec", gConsoleCommandExec);
        gConsoleSystem.registerCmd("exit", gConsoleCommandQuit);
        gConsoleSystem.registerCmd("listCmds", gConsoleCommandListCmds);
        gConsoleSystem.registerCmd("listCVars", gConsoleCommandListCVars);
        gConsoleSystem.registerCmd("listFilesCfg", gConsoleCommandListFilesCfg);
        gConsoleSystem.registerCmd("listFilesSprites", gConsoleCommandListSprites);
        gConsoleSystem.registerCmd("quit", gConsoleCommandQuit);
        gConsoleSystem.registerCmd("script", gConsoleCommandScript);
        gConsoleSystem.registerCmd("scriptFile", gConsoleCommandScriptFile);
        gConsoleSystem.registerCmd("set", gConsoleCommandSet);
        gConsoleSystem.registerCmd("seta", gConsoleCommandSetArchive);
        gConsoleSystem.registerCmd("vstr", gConsoleCommandVstr);
    }

    private void registerDefaultCVars() {
        cVarSystem.gCVar cVarRenderDims = new cVarSystem.gCVar(Arrays.toString(gGraphicsSystem.getRenderDims())) {
            @Override
            public void onChange() {
                String[] args = this.getValue().split(",");
                gGraphicsSystem.setRenderDims(new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])});
            }
        };

        cVarSystem.gCVar cVarWindowDims = new cVarSystem.gCVar(Arrays.toString(gGraphicsSystem.getWindowDims())) {
            @Override
            public void onChange() {
                String[] args = this.getValue().split(",");
                gGraphicsSystem.setWindowDims(new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])});
            }
        };

        cVarSystem.gCVar cVarRFullscreen = new cVarSystem.gCVar(gGraphicsSystem.getFullscreen() ? "1" : "0") {
            @Override
            public void onChange() {
                gGraphicsSystem.setFullscreen(this.getValue().equalsIgnoreCase("1"));
            }
        };

        gCVarSystem.registerCVar("r_fullscreen", cVarRFullscreen);
        gCVarSystem.registerCVar("r_windowDims", cVarWindowDims);
        gCVarSystem.registerCVar("r_renderDims", cVarRenderDims);
    }
}
