package com.android.launcher.can.status;

/**
 * @description: 35d
 *
 *  * D1 D2 D3 D4 D5 D6
 *  * 00 00 75 50 F0 E5
 *
 * @createDate: 2023/6/6
 */
public class Can35dStatus {


    /**
     * @description: 内部照明延迟时间
     * @createDate: 2023/6/6
     */
    public enum D1 {
        DELAY_0("00"), //延迟0秒
        DELAY_15("0F"),
        DELAY_30("1E"),
        DELAY_45("2D"),
        DELAY_60("3C"); //延迟60秒

        private String value;

        public String getValue() {
            return value;
        }

        D1(String value) {
            this.value = value;
        }
    }

    /**
     * @description: 外部照明延迟时间
     * @createDate: 2023/6/6
     */
    public enum D2 {
        DELAY_0("00"), //延迟0秒
        DELAY_15("0F"),
        DELAY_30("1E"),
        DELAY_45("2D"),
        DELAY_60("3C"); //延迟60秒

        private String value;

        public String getValue() {
            return value;
        }

        D2(String value) {
            this.value = value;
        }
    }


    /**
     * @description: 环境照明
     * @createDate: 2023/6/6
     */
    public enum D3 {
        DELAY_0("70"),
        DELAY_1("71"),
        DELAY_2("72"),
        DELAY_3("73"),
        DELAY_4("74"),
        DELAY_5("75");

        private String value;

        public String getValue() {
            return value;
        }

        D3(String value) {
            this.value = value;
        }
    }


//    /**
//    * @description: 外后视镜便捷设置
//    * @createDate: 2023/6/6
//    */
//    public enum D5 {
//
//        //锁车时自动合拢：关， 后驶时后视镜下降：关
//        STATUS_F0("F0"),
//        //后驶时后视镜下降: 开
//        STATUS_F1("F1"),
//        //锁车时自动合拢: 开
//        STATUS_F4("F4"),
//        //锁车时自动合拢: 开， 后驶时后视镜下降: 开
//        STATUS_F5("F5");
//
//        private String value;
//
//        D5(String value) {
//            this.value = value;
//        }
//
//        public String getValue() {
//            return value;
//        }
//    }

}
