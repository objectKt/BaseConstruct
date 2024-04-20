package com.backaudio.android.driver.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.backaudio.android.driver.manage.PubSysConst;

public class UtilTools {
	public static void setSystemProperties(String key, String value) {
		try {
			Class<?> clazz = Class.forName("android.os.SystemProperties");
			Method methodset = clazz.getMethod("set", String.class, String.class);
			methodset.setAccessible(true);
			methodset.invoke(null, key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getSystemProperties(String key, String str) {
		try {
			Class<?> clazz = Class.forName("android.os.SystemProperties");
			Method methodget = clazz.getMethod("get", String.class);
			methodget.setAccessible(true);
			String value = (String) methodget.invoke((Object) null, key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public static void setDSPSoundValue(boolean isDSP) {
		if (isDSP) {
			setSystemProperties("persist.audio.dsp", "1");
		} else {
			setSystemProperties("persist.audio.dsp", "0");
		}
	}
	public static void setCtrlvolume(boolean isopen) {
		 setSystemProperties("persist.smartpie.ctrlvolume",""+isopen);
	}
	public static String getFactoryModel() {
		
		return getSystemProperties("persist.factory.name", "");
	}
	public static void setFactoryModel(String name) {
		Log.i("deflauncher", "deflauncher : PackageName = "
				+ name);
		setSystemProperties("persist.factory.name", name);
	}
	
	public static String getFactoryLauncherModel() {
		return getSystemProperties("persist.factory.launcher.name", "");
	}
	public static void setFactoryLauncherModel(String name) {
		setSystemProperties("persist.factory.launcher.name", name);
	}
	public static void setImiProp(String str) {
		setSystemProperties("persist.sys.zhp.id", str);
	}

	public static void setZlinkBTName(String str) {
		setSystemProperties("rw.zlink.bt.name", str);
		setSystemProperties("rw.zlink.audiostream.media", "3");
		setSystemProperties("rw.zlink.audiostream.phonecall", "3");
		setSystemProperties("rw.zlink.audiostream.phonering", "3");
		setSystemProperties("rw.zlink.audiostream.tts", "3");
	}

	public static void setCarPlayOpen(boolean isopen) {
		if (isopen) {
			UtilTools.echoFile("1", PubSysConst.CARPLAY_ON);
		} else {
			UtilTools.echoFile("0", PubSysConst.CARPLAY_ON);
		}
	}

	public static void setZengYiSoundValue(int value) {
		Log.d("dddd", "set value:::" + value);
		setSystemProperties("persist.audio.vol", value + "");
	}

	public static int getZengYiSoundValue() {
		String string = getSystemProperties("persist.audio.vol", 0 + "");
		Log.d("dddd", "get value:::" + string);
		int aa = Integer.parseInt(string);
		return aa;
	}

	public static void setKillcode() {
		//setSystemProperties("ctl.start", "killcode");
	}

	public static void updateSystemOverseas() {
		setSystemProperties("ctl.start", "overseas");
	}
	public static void updateBootAnimation() {
		setSystemProperties("ctl.start", "change_media");
	}
	public static void updateSystemDomestic() {
		setSystemProperties("ctl.start", "domestic");
	}

	// 改变Launcher
	public static void updateSystemLauncher(String name) {
		setSystemProperties("app.name", "" + name);
		setSystemProperties("ctl.start", "change_app");
	}

	public static void closeNaviVoice(Context context) {
		Intent intent = new Intent("AUTONAVI_STANDARD_BROADCAST_RECV");
		intent.putExtra("KEY_TYPE", 12006);
		intent.putExtra("EXTRA_SETTING_TYPE", 1);
		intent.putExtra("EXTRA_SETTING_RESULT", false);
		intent.setPackage("com.autonavi.amapauto");
		context.sendBroadcast(intent);
	}

	// 运行在线获取的配置脚本
	public static void onlineOtaUpdate() {
		setSystemProperties("ctl.start", "ota_update");
	}

	// 格式化SDCard
	public static void resetSDCard() {
		// setSystemProperties("ctl.start", "mkfs");
		setSystemProperties("ctl.start", "data_clean");// 不清理地图数据
	}

	// 接管开机后触摸事件
	public static void stopKernelMCU() {
		setSystemProperties("ctl.stop", "kernel_mcu");
	}

	// APK升级
	public static void updateLauncherAPK() {
		setSystemProperties("ctl.start", "update_apk");
	}

	// 获取系统属性
	public static String getLauncherLanguage(Context mContext) {
		if (PubSysConst.is8CoreProject) {
			// return getSystemProperties("persist.sys.applanguage", "ch");
			String lan = getSystemProperties("persist.smartpie.version","");
			if(!TextUtils.isEmpty(lan)){
				return lan;
			}
		}
		return getSystemProperties("ro.smartpie.version", "ch");
	}

	// 单双蓝牙配置
	public static void changeDoubleBT(boolean isDoublebt, int isdoubleType) {
		Log.d("ddddddd", "isDoublebt::"+isDoublebt+"::isdoubleType::"+isdoubleType);
		
		
		if (PubSysConst.is8788 || PubSysConst.is8788Project
				|| PubSysConst.isMt6762|| PubSysConst.is6771Project
				|| PubSysConst.isWifiProject || PubSysConst.isHightTProject) {
			File file = new File("/data/blink");
			try {
				deleteFile(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isdoubleType == 0) {
				setSystemProperties("persist.blink", "0");
			} else if (isdoubleType == 2) {
				setSystemProperties("persist.blink", "2");
			} else {
				setSystemProperties("persist.blink", "1");
			}
		} else {
			if (isDoublebt) {
				setSystemProperties("ctl.start", "blink_rda5876");
			} else {
				setSystemProperties("ctl.start", "blink_built");
			}
		}

	}

	public static void changeDoubleBT(boolean isDoublebt) {
		if (isDoublebt) {
			setSystemProperties("ctl.start", "blink_rda5876");
		} else {
			setSystemProperties("ctl.start", "blink_built");
		}
	}

	/**
	 * 系统升级
	 * 
	 * @param context
	 */
	public static void setUpdateSystem(Context context) {
		Intent intent = new Intent();
		if (PubSysConst.isWifiProject || PubSysConst.is8788) {
			intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
			intent.setPackage("android");
		}
		intent.setAction("com.google.android.fotaupdate.intent.RECEIVE");
		context.sendBroadcast(intent);
	}

	/**
	 * 防止按钮连续点击
	 */
	private static long lastClickTime;
	private static Handler mHandler = new Handler();

	public static boolean isFastDoubleClick() {
		long time = SystemClock.elapsedRealtime();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			lastClickTime = time;
			return true;
		}
		lastClickTime = time;
		return false;
	}

	private static int clickCount = 0;

	public static boolean isFastClick(int num) {
		clickCount++;
		if (clickCount >= num) {
			clickCount = 0;
			return true;
		}
		if (clickCount != 0) {
			mHandler.removeCallbacks(resetClickCountThread);
			mHandler.postDelayed(resetClickCountThread, 300);
		}
		return false;
	}

	static Thread resetClickCountThread = new Thread(new Runnable() {

		@Override
		public void run() {
			clickCount = 0;
		}
	});

	/**
	 * 给系统发送模拟键值
	 * 
	 */
	public static void sendKeyEventToSystem(int keyCode) {
		if (keyCode < 0) {
			return;
		}
		final int keycode = keyCode;
		new Thread(() -> {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(keycode);
        }).start();
	}

	public static int offetX = 960;
	public static int offetY = 360;
	static {
		PubSysConst.MODEL= getFactoryModel();
			if(TextUtils.isEmpty(PubSysConst.MODEL)){
				PubSysConst.MODEL = Build.MODEL;
			}
		if (PubSysConst.MODEL.contains("hla800x480")) {
			offetX = 400;
			offetY = 240;
		}
	}
	/**
	 * 给系统发送模拟键值
	 * 
	 * @param keyeventCode
	 */
	static Handler handler = new Handler();
	static Timer mTimer;
	static int movement;
	static int i = 0;
	static boolean longClickEnter = false;
	static boolean isLandscape = false;

	public static void sendTouchToSystem(final Context context,final int action, final int code) {
		
		if(PubSysConst.isHightTProject6125){
			runShellCommand("input tap "+offetX+" "+offetY);
			return;
		}
		if (code == KeyEvent.KEYCODE_DPAD_CENTER) {
			movement = MotionEvent.ACTION_DOWN;
			new Thread(new Runnable() {

				@Override
				public void run() {
					Instrumentation inst = new Instrumentation();
					if (movement == MotionEvent.ACTION_DOWN) {
						Log.e("FloatViewService", "click ACTION_DOWN offetX = "
								+ offetX + ",offetY=" + offetY);
						inst.sendPointerSync(MotionEvent.obtain(
								SystemClock.uptimeMillis(),
								SystemClock.uptimeMillis()+100,
								MotionEvent.ACTION_DOWN, offetX, offetY, 0));
						inst.sendPointerSync(MotionEvent.obtain(
								SystemClock.uptimeMillis(),
								SystemClock.uptimeMillis()+100,
								MotionEvent.ACTION_UP, offetX, offetY, 0));
						
					}
				}
			}).start();
		}
	}

	public static void longClick() {
		if(PubSysConst.isHightTProject6125){
			runShellCommand("input swipe  "+offetX+" "+offetY+" "+offetX+" "+offetY+" 1000");
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				Log.d("FloatViewService",
						"<<<	onCarTouchBroad longClick : offetX = " + offetX
								+ ",offetY = " + offetY);
				Instrumentation inst = new Instrumentation();
				inst.sendPointerSync(MotionEvent.obtain(
						SystemClock.uptimeMillis(), SystemClock.uptimeMillis()+100,
						MotionEvent.ACTION_DOWN, offetX, offetY, 0));
				inst.sendPointerSync(MotionEvent.obtain(
						SystemClock.uptimeMillis()+150, SystemClock.uptimeMillis()+250,
						MotionEvent.ACTION_MOVE, offetX, offetY, 0));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				inst.sendPointerSync(MotionEvent.obtain(
						SystemClock.uptimeMillis(), SystemClock.uptimeMillis()+100,
						MotionEvent.ACTION_UP, offetX, offetY, 0));
			}
		}).start();

	}

	/**
	 * 获取图片ID
	 * 
	 * @param context
	 * @param idName
	 * @return
	 */
	public static int getResId(Context context, String idName) {
		try {
			return context.getResources().getIdentifier(idName, "drawable",
					context.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();
			//return R.drawable.ic_launcher;
			return 0;
		}
	}

	public static boolean isAvilible(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
	}

	/**
	 * 根据行读取内容
	 * 
	 * @return
	 */
	public static List<String> Txt(InputStream inStream) {
		// 将读出来的一行行数据使用List存储
		List<String> newList = new ArrayList<String>();
		try {
			int count = 0;// 初始化 key值
			InputStreamReader isr = new InputStreamReader(inStream);
			BufferedReader br = new BufferedReader(isr);
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				if (!"".equals(lineTxt)) {
					String reds = lineTxt.split("\\+")[0]; // java 正则表达式
					newList.add(count, reds);
					count++;
				}
			}
			isr.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newList;
	}

	/**
	 * 判断只有数字和字母、点号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigitOrChinese(String str) {
		String regex = "^[a-z0-9A-Z.]+$";
		return str.matches(regex);
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public static boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	@SuppressLint("MissingPermission")
	public static String getIMEI(Context context) {
		TelephonyManager tel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tel.getImei(0);
		if (TextUtils.isEmpty(imei)) {
			imei = tel.getDeviceId();
		}
		setImiProp(imei);
		return TextUtils.isEmpty(imei) ? "" : imei;
	}

	@SuppressLint("MissingPermission")
	public static String getNumber(Context context) {
		TelephonyManager tel = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// String prop = android.os.Build.HARDWARE;
		/*
		 * if(prop.contains("qcom")){ PubSysConst.isHightTProject = true; }
		 */
		if(tel.getImei(0) == null){
			return "0000";
		}
		String imei = tel.getImei(0).trim();
		
		if (TextUtils.isEmpty(imei)) {
			imei = tel.getDeviceId();
		}
		if(imei.trim().equals("0")){
			imei = "000000000000000";
		}
		/*
		 * if(PubSysConst.isHightTProject){ imei = tel.getImei(0); }else{ imei =
		 * tel.getDeviceId(); }
		 */
		String imeiShow = TextUtils.isEmpty(imei) ? "000000000000000" : imei;
		setImiProp(imei);
		return imeiShow.substring(imeiShow.length()-4);
	}

	public static boolean checkUSBExist(String path) {
		StatFs stat = getStatFs(path);
		long total = calculateTotalSizeInMB(stat);
		if (total > 0) {
			return true;
		}
		return false;
	}

	public static boolean checkUSBExist() {
		String path = "/mnt/usbotg";
		StatFs stat = getStatFs(path);
		long total = calculateTotalSizeInMB(stat);
		if (total > 0) {
			return true;
		}
		return false;
	}

	public static boolean checkTFCarkExist() {
		String path = "storage/sdcard1";
		StatFs stat = getStatFs(path);
		long total = calculateTotalSizeInMB(stat);
		if (total > 0) {
			return true;
		}
		return false;
	}

	private static StatFs getStatFs(String path) {
		try {
			return new StatFs(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long calculateTotalSizeInMB(StatFs stat) {
		if (stat != null) {
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return blockSize * totalBlocks;
		}
		return 0;
	}

	/**
	 * * 获取android当前可用运行内存大小 * @param context *
	 */
	public static String getAvailMemory(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		// mi.availMem; 当前系统的可用内存
		return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
	}

	/**
	 * * 获取android总运行内存大小 * @param context *
	 */
	public static String getTotalMemory(Context context) {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}
			// 获得系统总内存，单位是KB
			int i = Integer.valueOf(arrayOfString[1]).intValue();
			// int值乘以1024转换为long类型
			initial_memory = new Long((long) i * 1024);
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	/**
	 * 判断某个界面是否在前台
	 * 
	 * @param context
	 * @param className
	 *            某个界面名称
	 */
	public static boolean isForeground(Context context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean stringFilter(String str) {
		// 只允许字母和数字和中文//[\\pP‘’“”
		// String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
		String regEx = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";
		try {
			Pattern p = Pattern.compile(regEx);
			// StringBuilder sb = new StringBuilder(str);
			boolean flag = true;
			for (int len = str.length(), i = len - 1; i >= 0; --i) {
				Matcher m = p.matcher(String.valueOf(str.charAt(i)));
				if (!m.matches()) {
					flag = false;
					break;
				}
			}
			return flag;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取包名
	 * 
	 * @return 当前应用包名
	 */
	public static String getPackage(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.packageName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getCurrentTopAppPkg(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = mActivityManager
				.getRunningTasks(1);
		if (runningTaskInfos == null || runningTaskInfos.size() == 0) {
			return "";
		}
		String topPkg = runningTaskInfos.get(0).topActivity.getPackageName();
		return topPkg;
	}


	public static boolean echoFile(String str, String filepath) {
		Log.d("driverlog", "echoFile: " + str + " path: " + filepath);
		FileOutputStream out = null;
		try {
			File file = new File(filepath);
			if (file.exists() && file.canWrite()) {
				out = new FileOutputStream(file);
				out.write(str.getBytes());
				out.flush();
				return true;
			}
		} catch (Exception e) {
			Log.d("driverlog", "echofile err----------");
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					Log.d("driverlog", e.getMessage());
				}
			}
		}
		return false;
	}

	/**
	 * @desc: 是否存在U盘
	 * @return
	 */
	public static boolean isExistUsb() {
		List<String> filesAllName = getFilesAllName("/storage/");
		if(filesAllName == null){
			return false;
		}
		Log.d("dddd", "filesAllName::" + filesAllName.size());
		if (!filesAllName.contains("/storage/sdcard1")
				&& (filesAllName.size() > 3)) {
			return true;
		} else if (filesAllName.contains("/storage/sdcard1")
				&& (filesAllName.size() > 5)) {
			return true;
		} else {
			return false;
		}
	}

	public static List<String> getFilesAllName(String path) {
		List<String> pathList = new ArrayList<String>();
		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null) {
			Log.e("error", "空目录");
			return null;
		}

		for (int i = 0; i < files.length; i++) {
			// Log.d("nnnn", "::"+files[i].getAbsolutePath());
			pathList.add(files[i].getAbsolutePath());
		}
		return pathList;
	}

	public static long getInternalMemorySize(Context context) {
		File file = Environment.getDataDirectory();
		StatFs statFs = new StatFs(file.getPath());
		long blockSizeLong = statFs.getBlockSizeLong();
		long blockCountLong = statFs.getBlockCountLong();
		long size = blockCountLong * blockSizeLong;
		// return Formatter.formatFileSize(context, size);
		return size / 1024 / 1024 / 1024;
	}

	/** 判断程序是否在后台运行 */
	public static boolean isRunBackground(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					// 表明程序在后台运行
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public static String NaviFile = "/sys/bus/platform/drivers/mt_usb/mt_usb/audio";

	public static String getNaviIndex() {
		String content = ""; // 文件内容字符串
		// 打开文件
		File file = new File(NaviFile);
		InputStream instream = null;
		try {
			instream = new FileInputStream(file);
			if (instream != null) {
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line = buffreader.readLine();
				// 分行读取
				content = line;
				instream.close();
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	public static List<String> readTxt() {
		File file = new File("/system/navi.conf");
		InputStream inStream = null;
		if (file.exists()) {
			try {
				inStream = new FileInputStream(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 将读出来的一行行数据使用List存储
		List<String> newList = new ArrayList<String>();
		try {
			int count = 0;// 初始化 key值
			InputStreamReader isr = new InputStreamReader(inStream);
			BufferedReader br = new BufferedReader(isr);
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				if (!"".equals(lineTxt)) {
					String reds = lineTxt.split("\\+")[0]; // java 正则表达式
					newList.add(count, reds);
					count++;
				}
			}
			isr.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newList;
	}

	public static boolean isNaviPackagename(String name) {
		List<String> list = readTxt();
		for (int i = 0; i < list.size(); i++) {
			if (name.contains(list.get(i))) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<String> apkListPath = new ArrayList<String>();

	public static ArrayList<String> getAllApkFile(String path) {
		apkListPath.clear();
		File file = new File(path);
		String[] list = file.list();
		if (list == null) {
			return apkListPath;
		}
		for (int i = 0; i < list.length; i++) {
			// Log.d("oooo", "list[i]::"+list[i]);
			if (list[i].endsWith(".apk")) {
				apkListPath.add(path + "/" + list[i]);

			}
		}
		return apkListPath;
	}

	public static int number = 0;

	public static int getInstallNumber() {
		return number;
	}

	public static void setInstallNumber(int a) {
		number = a;
	}

	@SuppressLint("MissingPermission")
	public static void uninstall(Context mContext, String packageName) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent sender = PendingIntent
				.getActivity(mContext, 0, intent, 0);
		PackageInstaller mPackageInstaller = mContext.getPackageManager()
				.getPackageInstaller();
		mPackageInstaller.uninstall(packageName, sender.getIntentSender());// 卸载APK
	}

	public static String getPackageName(String FilePath, Context context) {
		String packageName = "";
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(FilePath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			String appName = pm.getApplicationLabel(appInfo).toString();
			packageName = appInfo.packageName; // 获取安装包名称
			Log.i("oooo", "包名是：" + packageName);
			String version = info.versionName; // 获取版本信息
			Log.i("oooo", "版本信息：" + version);

		}
		return packageName;
	}

	/**
	 * 
	 * 执行具体的静默安装逻辑，需要手机ROOT。
	 * 
	 * @param apkPath
	 * 
	 *            要安装的apk文件的路径
	 * 
	 * @return 安装成功返回true，安装失败返回false。
	 */

	public static boolean silentInstall(String apkPath, Context context) {
		boolean result = false;
		DataOutputStream dataOutputStream = null;
		BufferedReader errorStream = null;
		try {
			// 申请su权限
			Process process = Runtime.getRuntime().exec("su");
			dataOutputStream = new DataOutputStream(process.getOutputStream());
			// 执行pm install命令
			String command = "pm install -r " + apkPath + "\n";
			Log.d("oooo", "command::" + command);
			dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
			dataOutputStream.flush();
			dataOutputStream.writeBytes("exit\n");
			dataOutputStream.flush();
			process.waitFor();
			errorStream = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String msg = "";
			String line;
			// 读取命令的执行结果
			while ((line = errorStream.readLine()) != null) {

				msg += line;

			}

			Log.d("oooo", "install msg is " + msg);

			// 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功

			if (!msg.contains("Failure")) {

				result = true;

			}

		} catch (Exception e) {

			Log.e("TAG", e.getMessage(), e);

		} finally {

			try {

				if (dataOutputStream != null) {

					dataOutputStream.close();

				}

				if (errorStream != null) {

					errorStream.close();

				}

			} catch (IOException e) {

				Log.e("TAG", e.getMessage(), e);

			}

		}

		return result;

	}

	private static String[] chars = new String[] { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
			"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
			"v", "w", "x", "y", "z" };

	private static String backMD5(String inStr) {

		MessageDigest md5 = null;

		try {

			md5 = MessageDigest.getInstance("MD5");

		} catch (Exception e) {

			System.out.println(e.toString());
			e.printStackTrace();

			return "";
		}

		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {

			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();

	}

	public static String getKey(Context mContext) {
		String sn = UtilTools.getIMEI(mContext) + "mrw";
		String snMD5 = backMD5(sn);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			String str = snMD5.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			result = result.append(chars[x % 0x24]);
		}
		return result.toString();
	}

	/*
	 * public static String change(String changeText) {
	 * if(TextUtils.isEmpty(changeText)){ return ""; } changeText =
	 * ZHConverter.convert(changeText, ZHConverter.TRADITIONAL); return
	 * changeText; }
	 */

	public static String intToString(int[] dspValues) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dspValues.length; i++) {
			sb.append(dspValues[i] + ",");
		}
		return sb.toString();
	}

	public static int[] stringToInt(String dspString) {
		String str[] = dspString.split(",");
		int array[] = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			array[i] = Integer.parseInt(str[i]);
		}
		return array;
	}

	public static boolean deleteFile(File dirFile) {
		// 如果dir对应的文件不存在，则退出
		if (!dirFile.exists()) {
			return false;
		}

		if (dirFile.isFile()) {
			return dirFile.delete();
		} else {
			for (File file : dirFile.listFiles()) {
				// Log.d("dddd", "file::"+file+"::fileL:"+file.length());
				if (file != null && file.length() != 0) {
					deleteFile(file);
				} else {
					return false;
				}

			}
		}

		return dirFile.delete();
	}

	public static void hideApp(boolean isHide, String packageName) {
		if (isHide) {
			UtilTools.runShellCommand("pm hide " + packageName);
		} else {
			UtilTools.runShellCommand("pm unhide " + packageName);
	}
	}

	public static void hideLauncher(String packageName) {
		String[] applist = {"com.touchus.benchilauncher","com.touchus.aodilauncher",
				"com.touchus.bmwlauncher","com.smartpie.porschelauncher",
				"com.touchus.volvolauncher","com.touchus.mazdalauncher"}; 
		for (String string : applist) {
			if(TextUtils.equals(packageName, string)){
				UtilTools.hideApp(false, string);
			}else{
				UtilTools.hideApp(true, string);
			}
		}

	}
	public static void runShellCommand(final String command) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Process process = null;
				BufferedReader bufferedReader = null;
				StringBuilder mShellCommandSB = new StringBuilder();
				Log.d("wenfeng", "runShellCommand :" + command);
				mShellCommandSB.delete(0, mShellCommandSB.length());
				String[] cmd = new String[] { "/system/bin/sh", "-c", command }; // 调用bin文件
				try {
					byte b[] = new byte[1024];
					process = Runtime.getRuntime().exec(cmd);
					bufferedReader = new BufferedReader(new InputStreamReader(
							process.getInputStream()));
					String line;

					while ((line = bufferedReader.readLine()) != null) {
						mShellCommandSB.append(line);
					}
					Log.d("wenfeng",
							"runShellCommand result : " + mShellCommandSB.toString());
					process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (bufferedReader != null) {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							// TODO: handle exception
						}
					}

					if (process != null) {
						process.destroy();
					}
				}
			}
		}).start();
		
	}

	public static int getNumberOfCPUCores() {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
			return 1;
		}
		int cores;
		try {
			cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
		} catch (SecurityException e) {
			cores = 0;
		} catch (NullPointerException e) {
			cores = 0;
		}
		return cores;
	}

	private static final FileFilter CPU_FILTER = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			String path = pathname.getName();
			// regex is slow, so checking char by char.
			if (path.startsWith("cpu")) {
				for (int i = 3; i < path.length(); i++) {
					if (path.charAt(i) < '0' || path.charAt(i) > '9') {
						return false;
					}
				}
				return true;
			}
			return false;
		}
	};
	public static  String getLauncherValue() {
		String path = "/mnt/vendor/persist/launcher.txt";
		String content = ""; // 文件内容字符串
		File file = new File(path);
		InputStream instream = null;
		try {
			instream = new FileInputStream(file);
			if (instream != null) {
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line = buffreader.readLine();
				// 分行读取
				content = line;
				instream.close();
			}
		} catch (Exception e) {
			return "0";
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return content;
	}
	
	
	public static  void mkdir_folder_file(String path){
		           File folder = new File(path);
		           if(!folder.exists()){
		             folder.mkdirs();//创建目录
		           }
		           File test_f = new File(folder,"launcher.txt");
		           if(!test_f.exists()){
		             try {
		             test_f.createNewFile();//创建文件
		             } catch (IOException e) {
		              e.printStackTrace();
		            }
		          }
		        }
		
	public static   void write_file(String straa,String path){
		               mkdir_folder_file(path);
		           try{
		             File file = new File(path+"/launcher.txt");
		            FileOutputStream stream = new FileOutputStream(file,false);
		            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		             //Date date = new Date(System.currentTimeMillis());
		            //time1.setText("Date获取当前日期时间"+simpleDateFormat.format(date));
		            // String str = simpleDateFormat.format(date)+"::"+straa+"\n";
		             byte[] buf = straa.getBytes();
		             stream.write(buf);//写文件
		             stream.close();
		           }catch(Exception e){
		             e.printStackTrace();
		          }
	        }

}
