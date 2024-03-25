package dc.library.utils.global.type;

/**
 * @date： 2023/10/9
 * @author: 78495
*/
public enum DriveModeType {
    //01 经典 M 00 舒适 02 C  运动 s
    //经典
    CLASSIC("01"),
    //运动
    SPORT("02"),
    //舒适
    COMFORT("00");


    private String value;


    DriveModeType(String value) {
        this.value = value;
    }

    public String getTypeValue() {
        return value;
    }
}
