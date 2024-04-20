package com.backaudio.android.driver.manage;

import android.os.Environment;

public class PubSysConst {
	public static EProjectModel SOFTWARE_MODULE = EProjectModel.NONE;
	public static boolean DEBUG = false;
	public static boolean mediaListrefresh = false;
	public static boolean is8CoreProject = false;
	public static boolean is8788Project = false;
	public static boolean isMt6762 = false;
	public static boolean is6771Project = false;
	public static boolean is6739Project = false;
	public static boolean isHightTProject = false;
	public static boolean isHightTProject6125 = false;
	public static boolean isHightTProject8core = false;
	public static boolean is8788 = false;
	public static boolean isReverseAHD = false;
	public static boolean isCanboxLY = true;
	public static String isCanboxLYString = "isCanboxLYString";
	public static boolean isAudiCarNew = false;
	public static boolean is3gCoreProject = false;
	public static boolean isWifiProject = false;
	public static String LOCALLANGUAGE = "LocalLanguage";
	public static int LANGUAGE = 0; // 0-中文 1其他語言
	public static Boolean forceAUX = false;
	public static boolean isAMP = false; // 是功放与否
	//public static boolean isDecoder= false; // 是否是解码器
	public static int SCREEN_X = 1920;
	public static int SCREEN_Y = 720;
	public static int SCREEN_CENTER_X = 960;
	public static int SCREEN_CENTER_Y = 360;
	
	public static String KEY_ISDOUBLEBT = "isDoubleBT"; // 双蓝牙，默认
	public static boolean ISDOUBLEBT = false; // 双蓝牙，默认
	public static int ISDOUBLEBTTYPE = 0; // 双蓝牙，默认
	public static String ACTION_FACTORY_ISDOUBLEBT = "com.touchus.factorytest.isdoublebt";

	public static String IS_NEW_AUDI = "is_new_audi";
	public static String IS_AUDI_VOLVO = "is_audi_volvo";
	public static int isRevingQianTime = 0;
	public static final String ISREVINGQIANTIME = "revingqiantime";
	public static int reverseFrontViewPos = 0;
	// 标识 倒车前视
	public static final String FLAG_REVERSING_FRONTVIEW = "reversingfrontview";
	public static final String LOG_PATH = Environment.getExternalStorageDirectory().getPath()+"/unibroad/smartpieLogs/";

	/** 从屏幕边缘滑动的广播 **/
	public static String ACTION_FRAMEWORK_ONSWIPEFROMRIGHT = "com.smartpie.onswipefromright";
	public static String ACTION_FRAMEWORK_ONSWIPEFROMBOTTOM = "com.smartpie.onswipeFrombottom";
	public static String ACTION_FRAMEWORK_ONSWIPEFROMTOP = "com.smartpie.onswipefromtop";
	public static String ACTION_FRAMEWORK_ONSWIPEFROMLEFT = "com.smartpie.onswipefromleft";
	/** 左下角菜单广播监听 **/
	public static String ACTION_FRAMEWORK_OPENSTATENAVI = "com.unibroad.openstatenavi";
	public static String ACTION_FRAMEWORK_SCREENSHOT = "com.smartpie.screenshot";

	/** 工厂模式中断选择 **/
	public static String ACTION_FACTORY_BREAKSET = "com.touchus.factorytest.breakpos";
	public static String ACTION_FACTORY_SETCONFIG = "com.touchus.factorytest.settouchconfig";
	public static String ACTION_FACTORY_GETCONFIG = "com.touchus.factorytest.gettouchconfig";
	public static String KEY_BREAKPOS = "breakpos";
	public static String KEY_TOUCH_X = "revert_x";
	public static String KEY_TOUCH_Y = "revert_y";
	public static String KEY_KEY_SCREEN = "key_screen";
	public static String GT9XX_INT_TRIGGER = "/sys/bus/i2c/drivers/gt9xx/gt9xx_int_trigger";
	public static String GT9XX_INT_LR = "/sys/bus/i2c/drivers/gt9xx/gt9xx_revert_x";
	public static String GT9XX_INT_UPDN = "/sys/bus/i2c/drivers/gt9xx/gt9xx_revert_y";
	public static String GT9XX_KEY_SCREEN = "/sys/bus/i2c/drivers/gt9xx/gt9xx_key_screen";
	public static String GT9XX_INT_PRESS_SCREEN = "/sys/bus/i2c/drivers/gt9xx";
	/** 工厂模式中断选择 **/

	public static String ACTION_FACTORY_MAINVOLUME = "com.touchus.factorytest.mainvolume";
	public static int BT_MAIN_VOLUME = 23; // BT电话主音量
	public static int FL_MAIN_VOLUME = 23; // 小喇叭主音量
	public static int MEDIA_MAIN_VOLUME = 23; // 媒体主音量
	public static int MEDIA_BRIGHTNESS_WEIGHT = 16; // 背光權重
	public static String KEY_MEDIA_BRIGHTNESS_WEIGHT = "key_media_brightness_weight"; // 背光權重
	public static String KEY_BT_MAIN_VOLUME = "key_bt_main_volume"; // BT电话主音量
	public static String KEY_FL_MAIN_VOLUME = "key_fl_main_volume"; // 小喇叭主音量
	public static String KEY_MEDIA_MAIN_VOLUME = "key_media_main_volume"; // 媒体主音量
	public static int BT_MAIN_VOLUME_ECHO = 6; // BT电话回音调节
	public static String KEY_BT_MAIN_VOLUME_ECHO = "key_bt_main_volume_echo"; // BT电话回音

	
	public static String ACTION_FLOAT_NAVIVOICE = "com.touchus.float.naviVoice";
	public static String ACTION_FLOAT_MEDIAVOICE = "com.touchus.float.mediaVoice";
	public static String ACTION_FLOAT_CALLVOICE = "com.touchus.float.callVoice";
	public static String ACTION_FLOAT_BRIGHTNESS = "com.touchus.float.brightness";
	public static String ACTION_FLOAT_MUTE = "com.touchus.float.mute";
	public static String naviVoice = "naviVoice";
	public static String mediaVoice = "mediaVoice";
	public static String callVoice = "callVoice";
	public static String brightness = "brightness";
	public static String reverseBrightness = "reverseBrightness";
	public static int mediaVoiceNum = 10;
	public static int naviVoiceNum = 8;
	public static int callVoiceNum = 8;
	public static int brightnessNum = 80;
	public static int reverseBrightnessNum = 50;
	public static boolean isMainMute = false;
	public static boolean isNaviMute = false;
	public static boolean isMediaMute = false;
	public static int MRW_TYPE = 0;// 0、普通版，1、简易版
	public static String KEY_MRW_TYPE = "key_mrw_type";
	public static String MODEL = "";
	public static float DPIMultiple = (float) 1.0; // 160dpi倍数
	
	public static String CARPLAY_ON = "/sys/bus/i2c/drivers/goodix-ts/1-005d/carplay_on";
	public static String FL_SW = "/sys/bus/i2c/drivers/goodix-ts/1-005d/fl_sw";
	public static String RCA_SW = "/sys/bus/i2c/drivers/goodix-ts/1-005d/rca_sw";

	public static String ACTION_FACTORY_VIEW360TYPE = "touchus.factory.view360type";
	public static String KEY_VIEW360TYPE = "View360Type";
	public static String KEY_VIEW360TYPE1 = "View360Type1";
	public static int VIEW360TYPE = 0;
	public static int VIEW360TYPE1 = 0;

	public static String ACTION_FACTORY_ACCTIME = "touchus.factory.acctime";
	public static String KEY_ACCTIME = "acctime";
	public static int ACCTIME = 1;

	public static boolean isVGA360 = false;
	public static boolean isChangying = false;// 畅影网店logo标志

	public static boolean isAUX = false;//是否有AUX
	public static boolean isTV = false;//是否有TV
	public static boolean isRecord = false;//是否有记录仪
	public static boolean isLamp = false;//是否有氛围灯
	public static boolean isComfortControl = false;//是否有舒适控制
	public static boolean isFragranceControl = false;//是否有香氛控制
	public static String KEY_NON_AIO = "is_non_all_in_one";// 非一体机
	public static String KEY_AUX = "is_key_aux";
	public static String KEY_AUX_AHD = "is_key_aux_ahd";
	public static String KEY_RECORD = "is_key_record";
	public static String KEY_TV = "is_key_tv";
	public static String KEY_LAMP= "is_key_lamp";
	public static String KEY_COMFORT_CRTL= "is_key_comfort_ctrl";
	public static String KEY_FRAGRANCE_CRTL= "is_key_fragrance_ctrl";
	public static String KEY_GOOGLEAPP = "is_key_googleapp";
	public static String ACTION_FACTORY_APP_SET = "com.touchus.factorytest.appset";
	public static String ACTION_FACTORY_NON_AIO = "com.touchus.factorytest.nonaio";
	public static String ACTION_FACTORY_AUX = "com.touchus.factorytest.aux";
	public static String ACTION_FACTORY_TV = "com.touchus.factorytest.tv";
	public static String ACTION_FACTORY_GOOGLEAPP = "com.touchus.factorytest.googleapp";

	public enum EProjectModel {
		NONE("NONE"), BENZ("BENZ"), AUDI("AUDI"), BMW("BMW"), LANDROVER("LANDROVER"), JAGUAR(
				"JAGUAR"), LEXUS("LEXUS"), CADILLAC("CADILLAC"), PORSCHE(
				"PORSCHE"), TOUAREG("TOUAREG"), PATROL("PATROL"), VOLVO("VOLVO"), MASERATI(
				"MASERATI"), ROMEO("ROMEO"), MAZDA("MAZDA"), BJ("BJ"), LANDCRUISER("LANDCRUISER")
				, ACCORD("ACCORD"), INFINITI("INFINITI"), PRADO("PRADO"), ALPHARD("ALPHARD"), 
				WEY("WEY");

		private String name;

		EProjectModel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
	}
	
	public static String ACTION_BMW_STYLE_TYPE = "com.touchus.factorytest.bmw.style";
	public static String ACTION_AUDI_STYLE_TYPE = "com.touchus.factorytest.audi.style";
	public static String ACTION_KS_LAND_STYLE_TYPE = "com.touchus.factorytest.ks.land.style";
	public static String KEY_STYLE_TYPE = "key_style_type";//机型选择
	public static String KEY_ORIGINAL_RIGHT_PIC = "key_original_right_pic";
	public static String ACTION_STYLE_TYPE = "com.touchus.activity.style.type";
	public static String KEY_EPROJECTMODEL = "key_eprojectmodel";//机型版本
	
	public static String KEY_DOMESTIC_AND_OVERSEAS = "key_domestic_and_overseas";//国内海外
	public static int VERSION_LANGUAGE_TYPE = 0;//0-国内，1-海外
	public static String KEY_PROJECT_MODEL = "key_project_model";
	public static String KEY_KS_LAND_ROVER_STYLE = "key_ks_land_rover_style";
	public static String ACTION_DOMESTIC_AND_OVERSEAS = "com.touchus.domestic_and_overseas";
	public static String ACTION_ORIGINAL_RIGHT_PIC = "com.touchus.original_right_pic";

	public static String JULI_DANWEI = "juli_danwei";
	public static String SUDU_DANWEI = "sudu_danwei";
	public static String WENDU_DANWEI = "wendu_danwei";

	public static String ACTION_JULI_DANWEI = "action_juli_danwei";
	public static String ACTION_SUDU_DANWEI = "action_sudu_danwei";
	public static String ACTION_WENDU_DANWEI = "action_wendu_danwei";
	
	public static String ACTION_DOOR_OPEN = "action_door_open";
	public static String IS_DOOR_OPEN = "is_door_open";
	public static boolean isDoorShow = true;
	
	public static String ACTION_AIR_OPEN = "action_air_open";
	public static String IS_AIR_OPEN = "is_air_open";
	// 0原车切换，1方控语音，2声控语音，3混音模式，4收音机界面，5原车导航，6连接方式BT/AUX，7USB数量,8空调显示隐藏、9中断、10、BT/USB
	// 11、手势 ，12、功放切换,13、左右舵，14、音效開關
	public static byte[] storeData = new byte[] { 
			0x00, 0x00, 0x00, 0x00, 0x00,// 0、原车切换，1、方控语音，2、声控语音，3、混音开关，4、收音机界面，
			0x00, 0x00, 0x00, 0x00, 0x00,// 5、原车导航，6、连接方式BT/AUX，7、USB数量,8、空调显示隐藏、9、中断
			0x00, 0x00, 0x00, 0x00, 0x00,//10、BT/USB， 11、手势 ，12、功放切换,13、左右舵，14、音效開關
			0x00, 0x00, 0x00, 0x00, 0x00,//15、车门开关，16、音频所在列（1），17、音频所在行，18、音频所在列（2）,19
			0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00 };
	
	public static String ACTION_HASPARKING = "com.touchus.hasparking";
	public static String KEY_PARK = "KEY_PARK";//泊车使能开关
	public static boolean hasPARKING = false;//0-无，1-有
	
	public static int adjustFR = 0;//陀螺仪前后校准值
	public static String KEY_ADJUSTFR = "adjustFR";
	public static int adjustRL = 90;//陀螺仪左右校准值
	public static String KEY_ADJUSTRL = "adjustRL";
	public static String KEY_IS_MODEL_TYPE = "key_is_model_type";
	public static boolean isDecoder = false;// 解码器
	public static boolean isDecoder2 = false;// 解码器
	public static boolean isNewNaviVersion = false;//新更新导航声音版本
	public static boolean isBackState = false;//倒车状态
	public static boolean isOrgBack = true;//是否原车倒车
	public static boolean is360Back = false;//是否360倒车
	public static boolean isBackMute = true;//是否倒车静音
	public static boolean isbeforeBackInAndroid = true;//倒车前是否在加装
	public static boolean is20km = true;//20km/h
	
	public static final String FLAG_MUSIC_POS = "musicPos";
	public static final String FLAG_MUSIC_CURRENTPOS = "musicCurrentPos";
	public static final String FLAG_VIDEO_POS = "videoPos";
	public static final int UPDATE_CUR_MUSIC_PLAY_INFO = 7001;
	public static final int ONLINE_UPDATE_CHECK_FAIL = 7552;
	public static final int ONLINE_UPDATE_CHECK_SUCCESS = 7553;
	public static final int USB_LISTENER = 7002;
	public static final int UPDATE_MUSIC_PLAY_STATE = 7003;
	public static final int HT6125_BACK = 8889;
	public static float musicDecVolume = 0.3f;
	public static float musicNorVolume = 1.0f;
	
	public static String ACTION_FLOAT_NAVIAUTO = "com.touchus.float.naviauto";
	public static String ACTION_FLOAT_NAVIAUTO1 = "com.touchus.float.naviauto1";
	public static boolean isNaviSoundAuto = true;
	public static int isNaviSoundType = 0;
	
	public static final String KEY_FACTORY_FLAG = "factory_flag";
	public static  String[] factoryFlagItems = new String[] { "A", "B", "C","D", "E", "F1","F2", "F3", "F4"};

	public static final String KEY_KEYLEARN = "keylearn";
	public static final String ACTION_FACTORY_KEYLEARN = "touchus.factory.keylearn";
	public static final String KEY_KEYLEARN_RESULT = "isSuccessed";
	public static final String ACTION_FACTORY_KEYLEARN_REC = "touchus.factory.keylearn.receive";
	
	public static final String ACTION_MRW_LAND_ZHUJI_TYPE = "action_mrw_land_zhuji_type";
	public static final String ACTION_MRW_LAND_ANJIAN_TYPE = "action_mrw_land_anjian_type";
	public static final String ACTION_LAND_ROVER_CHEXING_TYPE = "action_land_rover_chexing_type";
	
	public static final String ACTION_MRW_LAND_ONE_TYPE = "action_mrw_land_one_type";
	public static final String ACTION_MRW_LAND_TWO_TYPE = "action_mrw_land_two_type";
	public static final String ACTION_MRW_LAND_THREE_TYPE = "action_mrw_land_three_type";
	
	public static final String ACTION_LAND_ROVER_VEDIO_TYPE = "action_land_rover_vedio_type";
	public static final String ACTION_LAND_ROVER_ANJIAN_TYPE = "action_land_rover_anjian_type";
	public static final String ACTION_LAND_ROVER_ORING_TYPE = "action_land_rover_oring_type";
	public static final String ACTION_LAND_ROVER_CHEXING_TYPE_TWO = "action_land_rover_oring_type_two";
	
	public static final String ACTION_VEDIO_SET = "action_vedio_set_type";
	public static final String ACTION_VEDIO_TYPE = "action_vedio_value_type";
	public static int[] dspValues  ={ 
		12, 12, 12, 12, 
		12, 12, 12, 12, 
		12, 12, 12, 12, 
		12, 12, 12, 12};
	public static boolean isDspModel = false;
	public static boolean isFwdModel = false;
	public static final String LAST_POI_X = "LAST_POI_X";
	public static final String LAST_POI_Y = "LAST_POI_Y";
	public static final String ACTION_SCREEN_SIX_EIGHT = "action_screen_six_eight";
	public static final String ACTION_IS_BEHINDDOORREVERSD = "action_is_behinddoorreversd";
	public static final String ACTION_IS_FRONTDOORREVERSD = "action_is_frontdoorreversd";
	public static final String IS_BEHINDDOORREVERSD = "is_behinddoorreversd";
	public static final String IS_FRONTDOORREVERSD = "is_frontdoorreversd";

	
	public static final String REVERSE_CHECKBOX_0 = "reverse_checkbox_0";
	public static final String REVERSE_CHECKBOX_1 = "reverse_checkbox_1";
	public static final String REVERSE_CHECKBOX_2 = "reverse_checkbox_2";
	public static final String REVERSE_CHECKBOX_3 = "reverse_checkbox_3";
	public static final String REVERSE_CHECKBOX_4 = "reverse_checkbox_4";
	public static final String REVERSE_CHECKBOX_5 = "reverse_checkbox_5";
	public static final String REVERSE_CHECKBOX_6 = "reverse_checkbox_6";
	public static final String REVERSE_CHECKBOX_7 = "reverse_checkbox_7";
	public static final String REVERSE_CHECKBOX_8 = "reverse_checkbox_8";
	public static final String REVERSE_CHECKBOX_9 = "reverse_checkbox_9";
	public static final String REVERSE_CHECKBOX_10 = "reverse_checkbox_10";
	
	public static final String ACTION_REVERSE_CHECKBOX = "action_reverse_checkbox";

	public static final String KEY_BALANCE_POI_X = "BALANCE_POI_X";
	public static final String KEY_BALANCE_POI_Y = "BALANCE_POI_Y";
	public static final int UPGRADE_ERROR = 46110;
	public static final int UPGRADE_FINISH = 46111;
	public static final int UPGRADE_UPGRADE_PER = 46112;
	public static final int UPGRADE_UPGRADE_PER_SYSTEM = 46113;
	public static final int UPGRADE_UPGRADE_PER_SYSTEM_VERIFYING = 46114;
	public static final int UPDATED_NEED_REBOOT = 46115;
	public static int BALANCE_POI_X = 0;
	public static int BALANCE_POI_Y = 0;
	
	public static String ACTION_ZLINK_BTSOUND_REQUEST = "zlink.btsound.request";
	public static String ACTION_ZLINK_BTSOUND_UNREQUEST = "zlink.btsound.unrequest";
	public static String ACTION_ZLINK_BT_ISCALLING = "zlink.bt.iscalling";

	public static String custumLogoPath = "/cache/media/bootanimation.zip";
	public static String custumLogoPath1 = "/cache/media/bootanimation-1.zip";

	public static String KEY_HLA_UI = "key_hla_ui";
	public static int HLAUIPos = 0;
	public static boolean iMirror = false; 
	public static boolean iShow = true; 
	public static int pos_pic_ru = 0; 
	public static int pos_ru = 0; 
	public static int view_width = 1280; 
	public static int trajectory_width = 500; 
	public static int trajectory_height = 350; 
	public static float density = 1; 
	
	public static final String EVENT_UNIBROAD_RADARDATA = "com.unibroad.radardata";
	
	public static String ACTION_FACTORY_STARTUPTYPE = "touchus.factory.startuptype";
	public static String KEY_STARTUPTYPE = "View360Type";
	public static int STARTUPTYPE = 0;
	
	public enum EScreenDPI {
		NONE(0), SCREEN_1920_720(1),SCREEN_1024_600(2),
		SCREEN_1280_480(3), SCREEN_800_480(4),
		SCREEN_1280_640(5),SCREEN_1280_720(6),
		SCREEN_1920_1200(7),SCREEN_800_1280(8),
		SCREEN_1920_1080(9),SCREEN_720_1920(10),
		SCREEN_720_1280(11),SCREEN_8775_1920_720(12);

		private int value;

		EScreenDPI(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
