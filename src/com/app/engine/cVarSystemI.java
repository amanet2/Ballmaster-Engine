package com.app.engine;

public interface cVarSystemI {
    interface gCVar {
        void onChange();  // called everytime a value is changed
        void onUpdate();  // called everytime a value is set, regardless of previous value
    }

    interface gCVarSystem {
        boolean registerCVar(cVarSystem.gCVar cVar);
        String getCVarValue(String name);
        String setCVarValue(String name, String value);
    }
}
