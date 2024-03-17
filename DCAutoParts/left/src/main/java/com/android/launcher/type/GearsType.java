package com.android.launcher.type;

/**
 * 档位类型
 */
public enum GearsType {
    //P挡
    TYPE_P("50"),
    TYPE_R("52"),
    TYPE_N("4E"),
    //D挡
    TYPE_D("01"),
    //D挡
    TYPE_D_OTHER("44"),
    TYPE_D1("D1"),
    TYPE_D2("D2"),
    TYPE_D3("D3"),
    TYPE_D4("D4"),
    TYPE_D5("D5"),
    TYPE_D6("D6");
    private String value;

    GearsType(String value) {
        this.value = value;
    }

    public String getTypeValue() {
        return value;
    }
}