package dc.library.auto.serial.entity;

import android.util.Log;

import java.io.File;
import java.util.Vector;

/**
 * 串口设备 Bean
 */
public class BeanSerialDriver {

    private static final String TAG = BeanSerialDriver.class.getSimpleName();
    private String mDriverName;
    private String mDeviceRoot;

    public BeanSerialDriver(String name, String root) {
        mDriverName = name;
        mDeviceRoot = root;
    }

    /**
     * 获取所有串口设备
     */
    public Vector<File> getDevices() {
        Vector<File> devices = new Vector<>();
        File dev = new File("/dev");
        if (!dev.exists()) {
            Log.i(TAG, "getDevices: " + dev.getAbsolutePath() + " 不存在");
            return devices;
        }
        if (!dev.canRead()) {
            Log.i(TAG, "getDevices: " + dev.getAbsolutePath() + "没有读取权限");
            return devices;
        }
        File[] files = dev.listFiles();
        for (File file : files) {
            if (file.getAbsolutePath().startsWith(mDeviceRoot)) {
                Log.i(TAG, "Found new device: " + file);
                devices.add(file);
            }
        }
        return devices;
    }

    public String getName() {
        return mDriverName;
    }
}