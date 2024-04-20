package lib.dc.enums;

/**
 * @date： 2024/3/5
 * @author: 78495
 */
public enum BottomBarPanelKeyType {
    //驾驶模式选择左按键
    DYNAMIC_LEFT("80"),
    DYNAMIC_RIGHT("40"),
    P("20"),
    CAR("10"),
    ON("08"),
    SILENCE("04"),
    VOLUME_UP("01"),
    VOLUME_DOWN("02");

    private String value;


     BottomBarPanelKeyType(String value) {
        this.value = value;
    }

    public String getTypeValue() {
        return value;
    }
}
