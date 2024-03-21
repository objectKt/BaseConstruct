package dc.library.auto.serial;

import java.io.File;
import java.io.FileDescriptor;
import java.util.logging.Logger;

/**
 * 串口
 */
public class SerialPort {

    /**
     * chmod 666 means that all users can read and write but cannot execute
     */
    boolean chmod666(File device) {
        if (device == null || !device.exists()) {
            return false;
        }
        try {
            /* Missing read/write permission, trying to chmod the file */
            Process su;
            su = Runtime.getRuntime().exec("/system/bin/su");
            String cmd = "chmod 666 " + device.getAbsolutePath() + "\n" + "exit\n";
            su.getOutputStream().write(cmd.getBytes());
            if (su.waitFor() == 0 && device.canRead() && device.canWrite()) {
                return true;
            }
        } catch (Exception e) {
            Logger.getGlobal().warning("Exception" + e);
        }
        return false;
    }

    // JNI
    public native static FileDescriptor openPort(String path, int baudrate, int flags);

    public static native void closePort();

    static {
        System.loadLibrary("SerialPort");
    }
}