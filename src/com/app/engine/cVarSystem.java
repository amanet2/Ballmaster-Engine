package com.app.engine;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class cVarSystem implements cVarSystemI {
    public static class gCVar implements cVarSystemI.gCVar{
        private String value;

        public gCVar(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public void onChange() {

        }

        @Override
        public void onUpdate() {

        }
    }

    public static class gCVarSystem implements cVarSystemI.gCVarSystem {
        private HashMap<String, gCVar> cVarMap;

        public gCVarSystem() {
            this.cVarMap = new HashMap<>();
        }

        @Override
        public boolean registerCVar(String name, gCVar cVar) {
            if(cVarMap.containsKey(name))
                return false;
            cVarMap.put(name, cVar);
            return true;
        }

        @Override
        public String getCVarValue(String name) {
            gCVar cvar = cVarMap.get(name);
            if(cvar == null) {
                System.out.printf("No cvar found for '%s'%n", name);
                return null;
            }
            return cvar.value;
        }

        @Override
        public String setCVarValue(String name, String value) {
            gCVar cvar = this.cVarMap.get(name);
            if(cvar == null) {
                System.out.printf("No cvar found for '%s'%n", name);
                return null;
            }

            String oldValue = cvar.value;
            cvar.value = value;
            cvar.onUpdate();
            if(!oldValue.equals(cvar.value))
                cvar.onChange();
            System.out.printf("Set value of cvar '%s' to '%s'%n", name, value);
            return cvar.value;
        }

        public Set<String> keySet() {
            return new TreeSet<>(cVarMap.keySet());
        }

        public void parseArgs(String[] args) {
            for(int i = 0; i < args.length; i++) {
                System.out.println("ARG: " + args[i]);
                if(getCVarValue(args[i]) != null && args.length > i+1) {
                    System.out.println("ARG VALUE: " + args[i+1]);
                    setCVarValue(args[i], args[i+1]);
                    i+=1;
                }
            }
        }
    }
}
