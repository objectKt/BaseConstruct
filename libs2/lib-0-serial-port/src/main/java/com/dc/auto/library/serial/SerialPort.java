/*
 * Copyright 2009 Cedric Priscal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dc.auto.library.serial;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 串口 JNI 与串口管理解耦
 */
public class SerialPort {



    /**
     * chmod 666 means that all users can read and write but cannot execute
     *
     * @param device
     * @return
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
            e.printStackTrace();
        }
        return false;
    }

    // JNI
    protected native static FileDescriptor open(String path, int baudrate, int flags);

    public static FileDescriptor openPort(String path, int baudrate, int flags){
       return open(path,baudrate,flags);
    }

    public static void closePort() {
        close();
    }

    protected static native void close();

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
        mFd = open(device.getAbsolutePath(), baudrate, flags);
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