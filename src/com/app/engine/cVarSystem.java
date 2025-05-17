package com.app.engine;

import java.util.HashMap;

public class cVarSystem implements cVarSystemI {
    public class gCVar implements cVarSystemI.gCVar{
        private String name;
        private String value;

        public gCVar(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void onChange() {

        }

        @Override
        public void onUpdate() {

        }
    }

    public class gCVarSystem implements cVarSystemI.gCVarSystem {
        private HashMap<String, gCVar> cVarMap;

        public gCVarSystem() {
            this.cVarMap = new HashMap<>();
        }

        @Override
        public boolean registerCVar(gCVar cVar) {
            if(cVarMap.containsKey(cVar.name))
                return false;
            cVarMap.put(cVar.name, cVar);
            return true;
        }

        @Override
        public String getCVarValue(String name) {
            gCVar cvar = cVarMap.get(name);
            if(cvar == null)
                return null;

            return cvar.value;
        }

        @Override
        public String setCVarValue(String name, String value) {
            gCVar cvar = this.cVarMap.get(name);
            if(cvar == null)
                return null;

            String oldValue = cvar.value;
            cvar.value = value;
            cvar.onUpdate();
            if(!oldValue.equals(cvar.value))
                cvar.onChange();
            return cvar.value;
        }
    }
}
