package dc.library.auto.serial;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    ////////////////////////////  以下是旧代码里的耦合用法，不推荐，暂时继续使用在老项目里 /////////////////

    private static final String TAG = "SerialPort";
    /*
     * Do not remove or rename the field mFd: it is used by native method close();
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    public SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {
        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            try {
                /* Missing read/write permission, trying to chmod the file */
                Process su;
                su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 666 " + device.getAbsolutePath() + "\n" + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        mFd = openPort(device.getAbsolutePath(), baudrate, flags);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
    }

    // Getters and setters
    public FileInputStream getInputStream() {
        return mFileInputStream;
    }

    public FileOutputStream getOutputStream() {
        return mFileOutputStream;
    }

    public FileDescriptor getmFd() {
        return mFd;
    }

    ////////////////////////////  以上是旧代码里的耦合用法，不推荐，暂时继续使用在老项目里 /////////////////
}