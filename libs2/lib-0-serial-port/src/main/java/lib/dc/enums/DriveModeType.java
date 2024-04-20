package lib.dc.enums;

/**
* @description: 驾驶模式
* @createDate: 2023/5/24
*/
public enum DriveModeType {

    C("00"),
    M("01"),
    S("02");

    private String status;

    DriveModeType(String status){
        this.status = status;
    }

    public String getValue(){
        return status;
    }
}
