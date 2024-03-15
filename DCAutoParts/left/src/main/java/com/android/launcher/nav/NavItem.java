package com.android.launcher.nav;

/**
* @description:
* @createDate: 2023/9/13
*/
public class NavItem {
    private int imgRes;
    private String name;
    private boolean selected;

    public NavItem(int imgRes, String name, boolean selected) {
        this.imgRes = imgRes;
        this.name = name;
        this.selected = selected;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
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