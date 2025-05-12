package com.app.engine;

public class cVarImpl implements cVar {
    private String key;
    private String value;

    public cVarImpl(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void onChange() {

    }

    public void onUpdate() {

    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String newValue) {
        String oldValue = this.value;
        this.value = newValue;
        this.onUpdate();
        if(!oldValue.equals(this.value)) {
            this.onChange();
        }
    }
}
