package com.backaudio.android.driver.manage;

public class PublicEnum {
    /**
     * 奔驰S控制
     *
     * @author 刘果佳
     */
    public enum ESControlKey {
        KEY_UP(0x00), LEFT_ADD(0x01), LEFT_DEC(0x02), RIGHT_ADD(0x03), RIGHT_DEC(0x04),
        RADAR_OFF(0x05), CAR_SPORT(0x06), CAR_HANG(0x07), ESP_OFF(0x08);

        private int code;

        ESControlKey(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    /**
     * 其他设置
     *
     * @author 刘果佳
     */
    public enum ECarOtherSet {
        NONE(0x00), AMPLIFIER(0x80)
        //底盘升降
        , SPORT(0x40), AMPLIFIER_SPORT(0xC0);

        private int code;

        ECarOtherSet(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    /**
     * 奔驰S控制中码易通
     *
     * @author 刘果佳
     */
    public enum EYTSControlKey {
        NONE(0x00), RADAR_OFF(0x01), CAR_SPORT(0x02), CAR_HANG(0x03), ESP_OFF(0x04),
        KEY_LEFT(0x05), KEY_RIGHT(0x06), TURN_LEFT(0x81), TURN_RIGHT(0x82);
        private int code;

        EYTSControlKey(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    /**
     * POWER OFF、RADIO、DISC、TV、GPS、BT、iPod、AUX、USB、SD、DVR、MENU、CAMERA、TPMS、手机互联
     */
    public enum EControlSource {
        POWER_OFF(0x00), RADIO(0x01), DISC(0x02), TV(0x03), GPS(0x04), BT(0x05), iPod(
                0x06), AUX(0x07), USB(0x08), SD(0x09), DVR(0x0A), MENU(0x0B), CAMERA(
                0x0C), TPMS(0x0D), MOBLIE_WEB(0x0E), FM(0x0F), AM(0x10);

        private int code;

        EControlSource(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    /**
     * 信息查询 0x01：请求基本信息、0x03：请求空调状态信息、0x04：请求后雷达状态、0x05：请求前雷达状态、0x06：请求车门信息、
     * 0x08：请求原车时间、0x0A：请求方向盘转角、0x12：请求当前源信息、0x35：请求仪表盘显示信息、0x7F：请求 CAN MCU
     * 软件版本；
     */
    public enum EModeInfo {
        BASE_INFO(0x01), AIR_CONDITION(0x03), REAR_RADAR(0x04), FRONT_RADAR(
                0x05), CAR_DOOR(0x06), ORIGINAL_TIME(0x08), OUTSIDE_TEMP(0x09),
        CAR_PSI(0x0D), PRADO_DSP(0x0F), WHEEL_ANGLE(0x0A), ORIGINAL_SOURCE(
                0x12), DASHBOARD_INFO(0x35), CAR_WARNING(0x7D), CANBOX_INFO(0x7F),
        CAR_RUNNING_INFO(0x7E), COMFORT_INFO(0x20),
        ;

        private int code;

        EModeInfo(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum MMIdriverEnum {
        POW(0x01), SEEK_ADD(0x02), SEEK_DES(0x03), NAVI(0x08), TEL(0x09), RADIO(
                0x0A), MEDIA(0x0B), CAR(0x0C), TONE(0x0D), UP(0X11), DOWN(0x12), LEFT(
                0x3a), RIGHT(0x3b), MENU(0x21), BACK(0x22), OK(0x31), ONE(0x32), TWO(
                0x33), THREE(0x34), Four(0x35), SIX(0x36), SEVEN(0x37), EIGHT(
                0x38), NINE(0x39), POSITON(0x40), UP_MENU(0x81), NEXT_MENU(0x82), TURN_ADD(
                0x84), TURN_DES(0x83), MEDIA_JIA(0x90),
        ;

        private int code;

        MMIdriverEnum(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EIdriverEnum {
        NONE(0x00), VOL_ADD(0x01), VOL_DES(0x02), NEXT(0x03), PREV(0x04), MODE(
                0x05), MUTE(0x06), BT(0x07), PICK_UP(0x08), HANG_UP(0x09), UP(
                0x0A), DOWN(0x0B), HOME(0x0C), PRESS(0x0D), RIGHT(0x0E), LEFT(
                0x0F), BACK(0x10), K_VOL_ADD(0x11), K_VOL_DES(0x12), RADIO(0x15), NAVI(
                0x16), MEDIA(0x17), POWER_OFF(0x18), SPEECH(0x19), ESC(0x1A), K_HOME(
                0x1B), SPEAKOFF(0x1C), STAR_BTN(0x20), CARSET(0x21), CALL_HELP(
                0x22), CALL_SOS(0x23), CALL_FIX(0x24), MEDIA_PREV(0x30), MEDIA_NEXT(0x31),
        MEDIA_PLAY_PAUSE(0x32), TURN_RIGHT(0x40), TURN_LEFT(
                0x41), T_PRESS(
                0x50), VOL_Enter(0x51), C_DYNAMIC_UP(0x58), C_DYNAMIC_DN(0x59), C_tel(
                0x60), K_UP(0x61), K_DOWN(0x62), K_MEDIA(0x63), K_ENTER(0x64), K_NAV(
                0x65), K_LEFT(0x66), K_RIGHT(0x67), K_RETURN(0x68), K_OK(0x69), C_pre_next(
                0x6A), K_SOURCE(0x6B), P_FM(0x6C), P_AUX(0x6D), P_SEEK_ADD(0x6E), P_SEEK_DEC(0x6F),
        P_DISC(0x70), P_AUTO_P(0x71), P_SCAN(0x72), P_1(0x73), P_2(0x74), P_3(0x75), P_4(0x76),
        P_5(0x77), P_6(0x78), P_VOL_ADD(0x79), P_VOL_DEC(0x7A), P_VOL_ENTER(0x7B),
        P_TURN_ADD(0x7C), P_TURN_DEC(0x7D), P_TURN_ENTER(0x7E), C_P_SCREEN(0x7F),
        ;

        private int code;


        EIdriverEnum(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum EIdriverEnum1 {
        NONE(0x00), MODE(0x01), K_UP(0x02), K_DOWN(0x03), K_MEDIA(0x04), BT(
                0x05), K_ENTER(0x06), MUTE(0x07), SPEECH(0x08), K_NAV(0x09), VOL_ADD(
                0x0A), VOL_DES(0x0B), K_LEFT(0x0C), K_RIGHT(0x0D), K_RETURN(
                0x0E), PICK_UP(0x0F), HANG_UP(0x10), K_OK(0X11), VOL_Enter(0x12), K_VOL_ADD(
                0x13), K_VOL_DES(0x14), NAVI(0x15), RADIO(0x16), C_tel(0x17), C_DYNAMIC_UP(
                0x18), C_DYNAMIC_DN(0x19), CARSET(0x1A), STAR_BTN(0x1B), BACK(0x1C), C_pre_next(
                0x1D), HOME(0x1E), T_PRESS(0x1F), TURN_LEFT(0x20), TURN_RIGHT(
                0x21), K_SOURCE(0x22), P_FM(0x23), P_AUX(0x24), P_SEEK_ADD(0x25), P_SEEK_DEC(0x26),
        P_DISC(0x27), P_AUTO_P(0x28), P_SCAN(0x29), P_1(0x2A), P_2(0x2B), P_3(0x2C), P_4(0x2D),
        P_5(0x2E), P_6(0x2F), P_VOL_ADD(0x30), P_VOL_DEC(0x31), P_VOL_ENTER(0x32),
        P_TURN_ADD(0x33), P_TURN_DEC(0x34), P_TURN_ENTER(0x35), LEFT(
                0x36), RIGHT(0x37), UP(0x38), DOWN(0x39), MEDIA(0x3A), POWER_OFF(0x3B),
        C_P_SCREEN(0x56),
        MEDIA_PREV(0xe0), MEDIA_NEXT(0xe1),
        MEDIA_PLAY_PAUSE(0xe2), NEXT(0xf1), PREV(0xf2), PRESS(0xf5), ESC(0xfA), K_HOME(
                0xfb), CALL_HELP(0xfc), CALL_SOS(0xfd), CALL_FIX(0xfe), SPEAKOFF(0xff);

        private int code;

        EIdriverEnum1(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EEqualizer {
        TREBLE(0X02), MIDTONES(0X01), BASS(0X00);

        private int code;

        private EEqualizer(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EBtnStateEnum {
        BTN_UP(0x00), BTN_DOWN(0x01), BTN_LONG_PRESS(0x02);

        private int code;

        EBtnStateEnum(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EUpgrade {
        ERROR(0x00), GET_HERDER(0x01), UPGRADING(0x02), FINISH(0x03);

        private int code;

        EUpgrade(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum ECanboxUpgrade {
        ERROR(0x00), READY_UPGRADING(0x01), FINISH(0x02);

        private int code;

        ECanboxUpgrade(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EReverseTpye {
        ADD_REVERSE(0x00), ORIGINAL_REVERSE(0x01), REVERSE_360(0x02), REVERSE_360_1(
                0x03), REVERSE_360_2(0x04), VGA_360_1(0x05), VGA_360_2(0x06);

        private int code;

        EReverseTpye(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EReverse360 {
        CLOSE(0X00), FRONT(0X01), REAR(0X02), LEFT(0X03), RIGHT(0X04);

        private int code;

        EReverse360(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum ECarLayer {
        ANDROID(0x10), RECORDER(0x20), DV(0x21), DTV(0x22), RECORDER_AHD(0x23), REVERSE_360(0x30), REVERSE_360_1(
                0x31), VGA_360_1(0x32), VGA_360_2(0x33), ADH_360(0x34), ORIGINAL(0x40), BT_CONNECT(
                0x41), ORIGINAL_REVERSE(0x42), SCREEN_OFF(0x60), REVERSE(0x50), REVERSE_AHD1(0x51),
        REVERSE_AHD2(0x52), REVERSE_AHD3(0x53), REVERSE_AHD4(0x54), QUERY(
                0xFF);

        private int code;

        ECarLayer(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EReverserMediaSet {
        MUTE(0x00), FLAT(0x01), NOMAL(0x02), QUERY(0xFF);

        private int code;

        EReverserMediaSet(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EAUXStutas {
        ACTIVATING(0x00), SUCCEED(0x01), FAILED(0x02);

        private int code;

        EAUXStutas(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EAUXOperate {
        ACTIVATE(0x01), DEACTIVATE(0x02);

        private int code;

        EAUXOperate(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum ELanguage {
        SIMPLIFIED_CHINESE(0x00), TRADITIONAL_CHINESE(0x01), US(0x02);

        private int code;

        ELanguage(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum GestureType {
        RIGHT(0x01), LEFT(0x02), UP(0x03), DOWN(0x04), FONT(0x05), BACK(0x06);

        private int code;

        GestureType(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EMCUAudioType {
        NONE(0x00), ANDROID(0x01), DV(0x02), DTV(0x03), MIC(0x04),
        RADIO(0x05), AUX1(0x06), AUX2(0x07), DAB(0x08);

        private int code;

        EMCUAudioType(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EACCTime {
        SECOND_10(0X00), MIN_2(0X01), MIN_5(0X02), MIN_10(0X03), MIN_30(0X04), HOUR_1(
                0X05), HOUR_2(0X06), HOUR_3(0X07);

        private int code;

        EACCTime(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum EDrivingModule {
        ECO(0X00), ORIGINAL(0X01), COMFORT(0X02), SPORT(0X03), RACING(0X04);

        private int code;

        EDrivingModule(int temp) {
            this.code = temp;
        }

        public byte getCode() {
            return (byte) code;
        }
    }

    public enum ERadioType {
        FM(0), AM(1);
        private int code;

        ERadioType(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 途乐空调面板按键
     */
    public enum EAirConPanelKey {

        NONE(0x00), KEY_POWER(0x01), KEY_K_SEEK_ADD(0x10),
        KEY_K_SEEK_DEC(0x11), KEY_K_VOL_ADD(0x12), KEY_K_VOL_DEC(0x13),
        KEY_K_MODE(0x14), KEY_K_PHONE(0x15), KEY_P_MUTE(0x20), KEY_P_RADIO(0x21),
        KEY_P_AUX(0x22), KEY_P_MEDIA(0x23), KEY_P_PRE(0x24), KEY_P_NEXT(0x25),
        KEY_P_AUTOP(0x26), KEY_P_SCANRPT(0x27),
        KEY_P_NAVI(0x28), KEY_P_AUDIO(0x29), KEY_P_NUM1(0x2A),
        KEY_P_NUM2(0x2B), KEY_P_NUM3(0x2C), KEY_P_NUM4(0x2D),
        KEY_P_NUM5(0x2E), KEY_P_NUM6(0x2F), KEY_A_FRONT(0x30),
        KEY_A_REAR(0x31), KEY_A_AUTO(0x32), KEY_A_LEFT_TEMP_ADD(0x33),
        KEY_A_LEFT_TEMP_DEC(0x34), KEY_A_RIGHT_TEMP_ADD(0x35), KEY_A_RIGHT_TEMP_DEC(0x36),
        KEY_A_ON(0x37), KEY_A_SPEED_ADD(0x38), KEY_A_SPEED_DEC(0x39), KEY_A_MODE(0x3A),
        KEY_A_AC(0x3B), KEY_A_DUAL(0x3C), KEY_A_EXTERNAL_CIRCLE(0x3D),
        KEY_A_INTERNAL_CIRCLE(0x3E), KEY_A_SPEED(0x3F), KEY_A_REAR_FOG(0x40),
        KEY_C_NAVI(0x50), KEY_C_MENU(0x51), KEY_C_RETURN(0x52),
        KEY_C_OK(0x53), KEY_C_UP(0x54), KEY_C_DOWN(0x55), KEY_C_LEFT(0x56),
        KEY_C_RIGHT(0x57), KEY_C_UPLEFT(0x58), KEY_C_UPRIGHT(0x59),
        KEY_C_DOWNLEFT(0x5A), KEY_C_DOWNRIGHT(0x5B), KEY_PHONE_DOWN(0x5C);

        private int code;

        EAirConPanelKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    //S223空调面板按键
    //0x00:无操作
    //0x01:左AUTO
    //0x02:左温加
    //0x03:左温减
    //0x04:左风速加
    //0x05:左风速减
    //0x06:MAXFRONT
    //0x07:cycle
    //0x08：off
    //0x09:右风速加
    //0x0a:右风速减
    //0x0b:右温加
    //0x0c:右温减
    //0x0d:右AUTO
    //0x0e:REAR
    //0x0f:rest
    //注：发完相应按键后，弹起要发0x00
    public enum S223AirConPanelKey {
        NONE(0x00),
        LEFT_AUTO(0x01),
        LEFT_TEMP_UP(0x02),
        LEFT_TEMP_DOWN(0x03),
        LEFT_WIND_UP(0x04),
        LEFT_WIND_DOWN(0x05),
        MAX_FRONT(0x06),
        //循环
        CYCLE(0x07),
        OFF(0x08),
        RIGHT_WIND_UP(0x09),
        RIGHT_WIND_DOWN(0x0A),
        RIGHT_TEMP_UP(0x0B),
        RIGHT_TEMP_DOWN(0x0C),
        RIGHT_AUTO(0x0D),
        REAR(0x0E),
        REST(0x0F);
        private int code;

        S223AirConPanelKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }


    //大旋钮按键
    //0x00：无操作
    //0x01:up
    //0x02:down
    //0x03:left
    //0x04:right
    //0x05:ok
    //0x06:sel inc
    //0x07:sel dec
    //注：发完相应按键后，弹起要发
    //0x00,sel不需要发0x00
    public enum KnobKey {
        NONE(0x00), UP(0x01), DOWN(0x02), LEFT(0x03), RIGHT(0x04), OK(0x05), BACK(0x08)
        //开机
        , POWER_ON(0x09)
        //左旋
        , LEFT_ROTATE(0x07)
        //右旋
        , RIGHT_ROTATE(0x06);
        private int code;

        KnobKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    public enum EAlphardKey {
        NONE(0x00), P_HOME(0x01), P_NAV(0x02), P_INFO(0x03),
        P_AUDIO(0x04), P_TEL(0x05), P_SETUP(0x06), P_TV(0x07),
        P_DEST(0x08), P_MAP(0x09), P_TRACK_UP(0x0A), P_TRACK_DN(0x0B),
        P_PWR(0x0C), P_VOL_ADD2(0x0D), P_VOL_DEC2(0x0E),
        P_TUNE_ADD2(0x0F), P_TUNE_DEC2(0x10);
        private int code;

        EAlphardKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    public enum EPeripheralControl {
        NONE(0x00), GLASS_UP(0x50), GLASS_DN(0x51),
        GLASS_ATOMIZE(0x52), TV_UP(0x53), TV_DN(0x54), LED(0x58);
        private int code;

        EPeripheralControl(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 途乐空调面板按键
     */
    public enum EAirConCANPanelKey {
        NONE(0x00), KEY_A_FRONT(
                0x01), KEY_A_REAR(0x02), KEY_A_AUTO(0x03), KEY_A_LEFT_TEMP_ADD(
                0x04), KEY_A_LEFT_TEMP_DEC(0x05), KEY_A_RIGHT_TEMP_ADD(0x06), KEY_A_RIGHT_TEMP_DEC(
                0x07), KEY_A_ON(0x08), KEY_A_SPEED_ADD(0x09), KEY_A_SPEED_DEC(
                0x0A), KEY_A_MODE(0x0B), KEY_A_AC(0x0C), KEY_A_DUAL(0x0D), KEY_A_EXTERNAL_CIRCLE(
                0x0E), KEY_A_INTERNAL_CIRCLE(0x0F);
        private int code;

        EAirConCANPanelKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 保时捷面板按键
     */
    public enum EPorschePanelKey {
        MMI_NONE(0x00), MMI_TUNER(0x01), MMI_MEDIA(0x02), MMI_PICKUP(0x03), MMI_PHONE(
                0x04), MMI_HANGDN(0x05), MMI_NAVI(0x06), MMI_MAP(0x07), MMI_SOURCE(
                0x08), MMI_ROLL1_LEFT(0x09), MMI_ROLL1_ENTER(0x0A), MMI_ROLL1_RIGHT(
                0x0B), MMI_SOUND(0x0C), MMI_INFO(0x0D), MMI_ROLL2_LEFT(0x0E), MMI_ROLL2_ENTER(
                0x0F), MMI_ROLL2_RIGHT(0x10), MMI_BACK(0x11), MMI_PRE(0x12), MMI_NEXT(
                0x13), MMI_CAR(0x14), MMI_OPTION(0x15), MMI_DISC(0x16);
        private int code;

        EPorschePanelKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }

    public enum EPradoDSPKey {
        SET_NONE(0x00), SET_VOL(0x01), SET_MUTE(0x02), SET_AUTO_VOL(0x03),
        SET_SURROUND(0x04), SET_HIGH(0x05), SET_MID(0x06), SET_LOW(0x07),
        SET_BAL(0x08), SET_FADER(0x09);
        private int code;

        EPradoDSPKey(int temp) {
            this.code = temp;
        }

        public int getCode() {
            return code;
        }
    }
}
