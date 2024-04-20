package com.backaudio.android.driver;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static final int TIME_OUT = 1000;
	public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/unibroad/driver";
	public static String AUDIO_SOURCE = PATH + "/audio_source.prop";
	public static String VIDEO_SOURCE = PATH + "/video_source.prop";

	private static final String droid_up = "/sys/bus/platform/drivers/unibroad_gpio_control/gpio_droid_up";

	public static final String usb_protocol = "/sys/bus/platform/drivers/mt_usb/mt_usb/usb_protocol";

	public static final String gpio_radar = "/sys/bus/platform/drivers/unibroad_gpio_control/gpio_rada";
	
	public static final String radio_frequency = "/sys/bus/i2c/drivers/qn8027/qn8027_mode";
	
	public static final String collison_level = "/sys/bus/i2c/drivers/DA380/da380_sensitivity";
	public static final String collison_happen = "/sys/bus/i2c/drivers/DA380/da380_flag";

	public static final String aux_connect_flag = "/sys/bus/platform/drivers/image_sensor/tw8836_reg";
	
	public static final void closeEdog() {
		echoFile("0", gpio_radar);
	}

	public static final void openEdog() {
		echoFile("1", gpio_radar);
	}
	
	public static final void closeRadioFrequency(){
		echoFile("0", radio_frequency);
	}
	
	public static final void openRadioFrequency(){
		echoFile("1", radio_frequency);
	}
	
	public static final void setCollisonCheckLevel(String level){
		echoFile(level, collison_level);
	}
	//0正常 1碰撞
	public static final void setCollisonHappenState(String state){
		echoFile(state, collison_happen);
	}
	
	public static final String getCollisonHappenState(){
		return catFile(collison_happen);
	}
	
	public static final String getAuxConnect(){
		return catFile(aux_connect_flag);
	}

	public static void switch2AUXSource() {
		echoFile("ff 01", aux_connect_flag);
		echoFile("02 4c", aux_connect_flag);
	}

	public static void switch2ReversingSource() {
		echoFile("ff 01", aux_connect_flag);
		echoFile("02 44", aux_connect_flag);
	}

	public static void switch2NoSource() {
		echoFile("ff 01", aux_connect_flag);
	}

	public static void settingReveringLight(String value){
		echoFile("ff 00", aux_connect_flag);
		echoFile(value, aux_connect_flag);
	}

	/**
	 * 后视镜上通知kernel， Android已经准备好
	 */
	public static final void readToWork() {
		echoFile("1", droid_up);
	}

	/**
	 * 针对后视镜等无MCU的设备，音频源/视频源信息保存到文件中
	 * 
	 * @param path
	 *            {@link Utils#AUDIO_SOURCE} or {@link Utils#VIDEO_SOURCE}
	 * @param source
	 * @return
	 */
	public static boolean saveSource(String path, String source) {
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			fos.write(source.getBytes());
			fos.flush();
		} catch (Exception e) {
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
		}
		return true;
	}

	public static String getSource(String path) {
		FileInputStream fis = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				return "";
			}
			fis = new FileInputStream(file);
			byte[] buf = new byte[fis.available()];
			int size = fis.read(buf);
			if (size == 0) {
				return "";
			}
			return new String(buf);
		} catch (Exception e) {
			return "";
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static String byteArrayToHexString(byte[] byteArray, int index,
			int len) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (byteArray == null || byteArray.length <= 0) {
			return null;
		}
		for (int i = index, j = 0; i < byteArray.length && j < len; i++, j++) {
			int v = byteArray[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String byteArrayToHexString(byte[] byteArray) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (byteArray == null || byteArray.length <= 0) {
			return null;
		}
		for (int i = 0; i < byteArray.length; i++) {
			int v = byteArray[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 为一个完整的协议计算校验位，不做校验，直接修改数据,之前的协议都写乱了，统一一个方法处理
	 * 
	 * @param buffer
	 * @return 成品协议数据流
	 */
	public static byte[] calcCheckSum(byte[] buffer) {
		// AA 55 length data.. check
		if (buffer.length < 5) {
			System.out.println("invalid buffer length:"
					+ Utils.byteArrayToHexString(buffer));
		}
		byte check = buffer[2];
		for (int i = 3; i < buffer.length - 1; i++) {
			check += buffer[i];
		}
		buffer[buffer.length - 1] = check;
		return buffer;
	}

	/* B2 -> 0xB2 */
	public static int stringToByte(String in, byte[] b) throws Exception {
		if (b.length < in.length() / 2) {
			throw new Exception("byte array too small");
		}
		int j = 0;
		StringBuffer buf = new StringBuffer(2);
		for (int i = 0; i < in.length(); i++, j++) {
			buf.insert(0, in.charAt(i));
			buf.insert(1, in.charAt(i + 1));
			int t = Integer.parseInt(buf.toString(), 16);
			System.out.println("byte hex value:" + t);
			b[j] = (byte) t;
			i++;
			buf.delete(0, 2);
		}
		return j;
	}

	public static int hex2int(char c) {
		return "0123456789abcdef".indexOf(c);
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
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
		return false;
	}

	public static String catFile(String filepath) {
		FileInputStream in = null;
		try {
			File file = new File(filepath);
			if (file.exists() && file.canRead()) {
				in = new FileInputStream(file);
				byte[] buf = new byte[in.available()];
				int r = in.read(buf, 0, buf.length);
				String str = new String(buf, 0, r);
				return str;
			}
		} catch (Exception e) {
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static void bash(String cmdLine) throws Exception {
		Process process = Runtime.getRuntime().exec(cmdLine, null,
				new File("/system/bin"));
		InputStream input = process.getInputStream();
		BufferedReader reader = null;
		try {
			String buffer = null;
			reader = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			while ((buffer = reader.readLine()) != null) {
				Log.d("", cmdLine + "\r\n" + buffer);
			}
			reader = new BufferedReader(new InputStreamReader(input));
			while ((buffer = reader.readLine()) != null) {
				Log.d("", cmdLine + "\r\n" + buffer);
			}
			process.waitFor();
			int exitValue = process.exitValue();
			if (exitValue != 0) {
				throw new Exception("cmd [" + cmdLine + "]exit:" + exitValue);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(input != null){
				input.close();
			}
			if(reader != null){
				reader.close();
			}
		}
	}

	public static void system(String cmdLine) throws Exception {
		Process process = Runtime.getRuntime().exec(cmdLine);
		InputStream input = process.getInputStream();
		BufferedReader reader = null;
		String buffer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			while ((buffer = reader.readLine()) != null) {
				Log.d("", cmdLine + "\r\n" + buffer);
			}
			reader = new BufferedReader(new InputStreamReader(input));
			while ((buffer = reader.readLine()) != null) {
				Log.d("", cmdLine + "\r\n" + buffer);
			}
			process.waitFor();
			int exitValue = process.exitValue();
			if (exitValue != 0) {
				throw new Exception("cmd [" + cmdLine + "]exit:" + exitValue);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(input != null){
				input.close();
			}
			if(reader != null){
				reader.close();
			}
		}
	}
	public static String saveLogLine(String line, boolean isbyte) throws IOException{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		long timestamp = System.currentTimeMillis()/1000/60/60;;
		String time = formatter.format(new Date()).substring(0,10);
		String fileName = "log-" + time + "-" + timestamp + ".log";
		String logPath;
		if (isbyte){
			logPath = path + "/unibroad/benzbluetoothlog_byte/";
		} else {
			logPath = path + "/unibroad/benzbluetoothlog_line/";
		}
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(logPath);
			if(logFileSize(dir)>5.0){
				deleteDirectory(logPath);
	    	}
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(logPath + fileName,true);
			fos.write(line.toString().getBytes());
			fos.write(new byte[]{0x0d, 0x0a});
			fos.close();
		}
		return fileName;
	}

	private static String path =  Environment.getExternalStorageDirectory().getAbsolutePath();

	public static double logFileSize(File dir){
		  //判断文件是否存在     
        if (dir.exists()) {     
            //如果是目录则递归计算其内容的总大小    
            if (dir.isDirectory()) {     
                File[] children = dir.listFiles();     
                double size = 0;     
                for (File f : children)     
                    size += logFileSize(f);     
                return size;     
            } else {//如果是文件则直接返回其大小,以“兆”为单位   
                double size = (double) dir.length() / 1024 / 1024;        
                return size;     
            }     
        } else {     
            return 0.0;     
        }     
	}
	
	private static boolean flag;
	public static boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	
	public static boolean deleteFile(String sPath) {  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
}
