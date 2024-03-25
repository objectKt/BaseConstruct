package com.android.launcher.menu;

public class MenuItem {

    private MenuId menuId;

    private String name;

    private int drawableId;

    private boolean selected;


    public MenuItem(MenuId menuId, String name, int drawableId) {
        this.menuId = menuId;
        this.name = name;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public MenuItem(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    public enum MenuId{
        Tech_METER,
        SPORT_METER,
        CLASSIS_METER,
        MAP,
        MAINTAIN,
        FM,
        MEDIA,
        PHONE
    }
}
