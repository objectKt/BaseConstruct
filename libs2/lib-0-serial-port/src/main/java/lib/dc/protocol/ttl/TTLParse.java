package lib.dc.protocol.ttl;

import lib.dc.protocol.SerialPortTTLDecoder;
import com.dc.android.launcher.basic.utilx.XxQueue;

public class TTLParse {

    private static final String TAG = TTLParse.class.getSimpleName();

    private static TTLParse instance;

    public static TTLParse getInstance() {
        if (instance == null) {
            synchronized (TTLParse.class) {
                if (instance == null) {
                    instance = new TTLParse();
                }
            }
        }
        return instance;
    }

    public void pushTTL(byte[] buffer) {
        String decodeResult = SerialPortTTLDecoder.INSTANCE.decodeBuffer(buffer);
        if (decodeResult.isEmpty()) {
            return;
        }
        XxQueue.INSTANCE.putString(decodeResult);
    }
}
