package com.android.launcher.vo;


/**
 *保养
 */
public class MaintainItem {

    private String name;
    private boolean selected;


    public MaintainItem(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
