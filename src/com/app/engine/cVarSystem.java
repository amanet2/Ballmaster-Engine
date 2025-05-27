package com.app.engine;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import com.app.engine.utils.gDict;

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
        private HashMap<String, gCVar> internalMap;

        public gCVarSystem() {
            this.internalMap = new HashMap<>();
        }

        @Override
        public String registerCVar(String name, gCVar cVar) {
            if(this.internalMap.containsKey(name))
                return String.format("CVar name '%s' is already registered for CVarSystem '%s'", name, this);
            this.internalMap.put(name, cVar);
            return String.format("Registered new CVar '%s'", name);
        }

        @Override
        public String getCVarValue(String name) {
            gCVar cvar = this.internalMap.get(name);
            if(cvar == null)
                return String.format("No cvar found for '%s'", name);
            return cvar.value;
        }

        @Override
        public String setCVarValue(String name, String value) {
            gCVar cvar = this.internalMap.get(name);
            if(cvar == null)
                return String.format("No cvar found for '%s'", name);

            String oldValue = cvar.value;
            cvar.value = value;
            cvar.onUpdate();
            if(!oldValue.equals(cvar.value))
                cvar.onChange();
            return String.format("Set value of cvar '%s' to '%s'", name, value);
        }

        public Set<String> keySet() {
            return new TreeSet<>(this.internalMap.keySet());
        }

        public String[] getCVarList() {
            return this.keySet().toArray(new String[0]);
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

        public gDict toDict() {
            gDict dict = new gDict();
            for(String k : keySet()) {
                dict.put(k, this.internalMap.get(k).value);
            }
            return dict;
        }
    }
}
