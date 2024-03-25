package com.android.launcher.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.android.launcher.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LogcatHelper {

	public static LogcatHelper INSTANCE = null;
	private static String PATH_LOGCAT;
	private LogDumper mLogDumper = null;
	private int mPId;
	public static ACache aCache = ACache.get(App.getGlobalContext());

	/**
	 *
	 * 初始化目录
	 *
	 * */
	public void init(Context context) {
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
				PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Download";
			} else {// 如果SD卡不存在，就保存到本应用的目录下
				PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
						+ File.separator + "Download";
			}
			File file = new File(PATH_LOGCAT);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LogcatHelper getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new LogcatHelper(context);
		}
		String logCatindex= aCache.getAsString("LogCat");

		if (logCatindex==null){
			aCache.put("LogCat","000");
		}

		return INSTANCE;
	}

	private LogcatHelper(Context context) {
		init(context);
		mPId = android.os.Process.myPid();
	}


	/**
	 * @description: 清除多余的日志
	 * @createDate: 2023/9/22
	 */
	public void clearUnnecessaryLog() {
		File file = new File(PATH_LOGCAT);
		LogUtils.printI(LogcatHelper.class.getSimpleName(), "clearUnnecessaryLog---path=" + PATH_LOGCAT);
		if (file.exists()) {
			try {
				File[] files = file.listFiles();
				List<File> fileList = Arrays.asList(files);
				LogUtils.printI(LogcatHelper.class.getSimpleName(), "clearUnnecessaryLog---file-size=" + fileList.size());
				if (fileList.size() > 20) {
					Collections.sort(fileList, (o1, o2) -> {
						if (o1.isDirectory() && o2.isFile()) {
							return -1;
						}
						if (o1.isFile() && o2.isDirectory()) {
							return 1;
						}
						return o1.getName().compareTo(o2.getName());
					});

					int removeCount = fileList.size() - 20;

					LogUtils.printI(LogcatHelper.class.getSimpleName(), "clearUnnecessaryLog---removeCount=" + removeCount);

					for (int i = 0; i < removeCount; i++) {
						File removeFile = fileList.get(i);
						removeFile.delete();
					}
				}

				double storageSize = StorageUtils.getAvailableStorageSize();
				LogUtils.printI(LogcatHelper.class.getSimpleName(), "storageSize=" + storageSize);

				final double thresholdValue = 2.0;
				if (storageSize < thresholdValue) {
					if (fileList.size() == 1) {
						File removeFile = fileList.get(0);
						removeFile.delete();
					} else if (fileList.size() > 1) {
						for (int i = 0; i < fileList.size() - 1; i++) {
							File removeFile = fileList.get(i);
							removeFile.delete();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		try {
			LogUtils.printI(LogcatHelper.class.getSimpleName(), "start---------");
			if (mLogDumper == null) {
				mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
			}
			mLogDumper.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		LogUtils.printI(LogcatHelper.class.getSimpleName(),"stop----");
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
	}

	private class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			try {
				File file = new File(dir, "left-"
						+ getFileName() + ".log");
				out = new FileOutputStream(file);

				LogUtils.printI(LogcatHelper.class.getSimpleName(),"LogDumper----file="+file.getName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			/**
			 *
			 * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
			 *
			 * 显示当前mPID程序的 E和W等级的日志.
			 *
			 * */

			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
			// cmds = "logcat -s way";//打印标签过滤信息
			cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

		}

		public void stopLogs() {
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(
						logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((getDateEN()+ "  " + line + "\n")
								.getBytes());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}

			}

		}

	}

	public static String getFileName() {

		String logCatIndex =aCache.getAsString("LogCat") ;
		Log.i("logcatindex",(logCatIndex ==null )+"---------------------"+logCatIndex.equals("")+"====="+logCatIndex) ;
		if (logCatIndex ==null || logCatIndex.equals("")){
			logCatIndex="000" ;
		}else{
			String preIndex = "000"+logCatIndex ;
			Log.i("logcatindex",preIndex+"===1=") ;
			logCatIndex = preIndex.substring(preIndex.length()-3,preIndex.length()) ;
			Log.i("logcatindex",logCatIndex+"===2=") ;
		}

		String name = "benz-"+logCatIndex ;

		if(logCatIndex.equals("999")){
			logCatIndex = "000" ;
		}
		int index = Integer.parseInt(logCatIndex)+1 ;
		aCache.put("LogCat",index+"" );
		return name;
	}


	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;// 2012-10-03 23:41:31
	}

}


