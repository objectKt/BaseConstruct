package dc.library.auto.serial;

import android.util.Log;

import dc.library.auto.serial.entity.BeanSerialDevice;
import dc.library.auto.serial.entity.BeanSerialDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * 查找串口
 */
public class SerialPortFinder {

    private static final String TAG = "SerialPort";

    private Vector<BeanSerialDriver> mDrivers = null;

    Vector<BeanSerialDriver> getDrivers() throws IOException {
        if (mDrivers == null) {
            mDrivers = new Vector<>();
            LineNumberReader r = new LineNumberReader(new FileReader("/proc/tty/drivers"));
            String l;
            while ((l = r.readLine()) != null) {
                // Since driver name may contain spaces, we do not extract driver name with split()
                String drivername = l.substring(0, 0x15).trim();
                String[] w = l.split(" +");
                if ((w.length >= 5) && (w[w.length - 1].equals("serial"))) {
                    Log.d(TAG, "Found new driver " + drivername + " on " + w[w.length - 4]);
                    mDrivers.add(new BeanSerialDriver(drivername, w[w.length - 4]));
                }
            }
            r.close();
        }
        return mDrivers;
    }

    public ArrayList<BeanSerialDevice> getDevices() {
        ArrayList<BeanSerialDevice> devices = new ArrayList<>();
        try {
            Vector<BeanSerialDriver> drivers = getDrivers();
            for (BeanSerialDriver driver : drivers) {
                String driverName = driver.getName();
                Vector<File> driverDevices = driver.getDevices();
                for (File file : driverDevices) {
                    String devicesName = file.getName();
                    devices.add(new BeanSerialDevice(devicesName, driverName, file));
                }
            }
        } catch (IOException e) {
            Logger.getGlobal().warning("Exception" + e);
        }
        return devices;
    }
}