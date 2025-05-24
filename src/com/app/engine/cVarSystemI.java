package com.app.engine;

import java.util.Set;

public interface cVarSystemI {
    interface gCVar {
        void onChange();  // called everytime a value is changed
        void onUpdate();  // called everytime a value is set, regardless of previous value
        String getValue();
    }

    interface gCVarSystem {
        boolean registerCVar(String name, cVarSystem.gCVar cVar);
        Set<String> keySet();
        String getCVarValue(String name);
        String setCVarValue(String name, String value);
    }
}
