package com.backaudio.android.driver.bluetooth;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.backaudio.android.driver.util.UtilTools;
import com.drake.logcat.LogCat;

/**
 * 蓝牙模块访问,采用单例模式
 *
 * @author hknaruto
 * @date 2014-1-15 下午1:59:20
 */
public class Bluetooth {

	private static final String TAG = Bluetooth.class.getSimpleName();


    /** 蓝牙模块任高的 为true,速鼎、文强的为false **/
    private static boolean isRenGao = false;
    private static Bluetooth instance = null;

    @SuppressWarnings("unused")
	private static final String GPIO_BT = "/sys/bus/platform/drivers/unibroad_gpio_control/gpio_bt";
    @SuppressWarnings("unused")
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

    public static Bluetooth getInstance() throws Exception {
        synchronized (Bluetooth.class) {
            if (instance == null) {
                instance = new Bluetooth();
            }
        }
        return instance;
    }

    private void ioWrite(String cmd) throws Exception {
        byte[] buffer = cmd.getBytes();
        String str = new String(buffer);
		LogCat.d(TAG +"send cmd:" + str);
     // 包装协议
//        byte[] mcuProtocol = wrap(buffer);
        writeBluetooth(buffer);
    }

    //不用包装
    protected byte[] wrap(byte[] buffer) {
//        byte[] mcuProtocol = new byte[buffer.length + 6];
//        mcuProtocol[0] = (byte) 0xAA;
//        mcuProtocol[1] = 0x55;
//        mcuProtocol[2] = (byte) (buffer.length + 2);// 子协议号，类型各一个字节
//        mcuProtocol[3] = 0x01;
//        mcuProtocol[4] = 0x0F;
//        byte check = mcuProtocol[2];
//        check += (byte) (mcuProtocol[3] + mcuProtocol[4]);
//        for (int i = 0; i < buffer.length; i++) {
//            mcuProtocol[5 + i] = buffer[i];
//            check = (byte) (check + buffer[i]);
//        }
//        mcuProtocol[buffer.length + 5] = check;
        
        byte[] mcuProtocol = new byte[buffer.length + 4];
        mcuProtocol[0] = (byte) (buffer.length + 2);// 子协议号，类型各一个字节
        mcuProtocol[1] = 0x01;
        mcuProtocol[2] = 0x0F;
        byte check = mcuProtocol[0];
        check += (byte) (mcuProtocol[1] + mcuProtocol[2]);
        for (int i = 0; i < buffer.length; i++) {
            mcuProtocol[3 + i] = buffer[i];
            check = (byte) (check + buffer[i]);
        }
        mcuProtocol[buffer.length + 3] = check;
        return mcuProtocol;
    }


    /**
     * 通过MCU，将协议写入到蓝牙模块
     *
     * @param buffer
     * @throws Exception
     */
    public void writeBluetooth(byte[] buffer) throws Exception {
        // 写入到mcu
    	InitBTSerialPort.getInstance().writeBlueTooth(buffer);
		LogCat.d(TAG +"bluetoothprotocal write::"+ new String(buffer));
    }


    // //////////////////////////////////////////////////////////////

    public void clearPairingList() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+DLPD=000000000000");
		}else {
			ioWrite("AT#CV\r\n");
		}
    }

    /**
     * 读取版本,事件返回 VersionProtocol
     *
     * @throws Exception
     */
    public void readVersionSync() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+GVER\r\n");
		}else {
			ioWrite("AT#MY\r\n");
		}
    }
    // 设置蓝牙音量
    public void setBTVolume(int volume) throws Exception {
    	if (isRenGao) {
		}else {
			ioWrite("AT#VF"+volume+"\r\n");
		}
    	
    }
    // 读取MAC地址
    public void readDeviceAddr() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+GLBA\r\n");
		}else {
			ioWrite("AT#DF\r\n");
		}
    	
    }
    // 读取PIN码
    public void readDevicePIN() throws Exception {
    	if (isRenGao) {
    	}else {
    		ioWrite("AT#MN\r\n");
    	}
    	
    }

    /**
     * 读取本地设备名称, 事件返回 DeviceNameProtocol
     *
     * @throws Exception
     */
    public void readDeviceNameSync() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+GLDN\r\n");
		}else {
			ioWrite("AT#MM\r\n");
		}
    	
    }
    
    /**
     * 读取配对列表， 事件返回 PairingListProtocol
     *
     * @throws Exception
     */
    public void readPairingListSync() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+LSPD\r\n");
		}else {
			ioWrite("AT#MX\r\n");
		}
    	
    }
    
    /**
     * 蓝牙音乐是否静音
     * @param isMute  true 静音   
     * @throws Exception
     */
    public void setBluetoothMusicMute ( boolean isMute ) throws Exception {
    	if (isRenGao) {
		}else {
			if (isMute) {
	    		ioWrite("AT#VA\r\n");
	    	} else {
	    		ioWrite("AT#VB\r\n");
	    	}
		}
    }

    /**
     * 进入配对模式， 事件返回 EnterPairingModeResult
     *
     * @throws Exception
     */
    public void enterPairingModeSync() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+EPRM=1\r\n");
		}else {
			ioWrite("AT#CA\r\n");
		}
    }

    /**
     * 离开配对模式， 事件返回 EnterPairingModeResult
     *
     * @throws Exception
     */
    public void leavePairingModeSync() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+EPRM=0\r\n");
		}else {
			ioWrite("AT#CB\r\n");
		}
    	
        
    }

    /**
     * 来电时 挂断电话
     *
     * @throws Exception
     */
    public void hangUpThePhone() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFCHUP\r\n");
		}else {
			ioWrite("AT#CF\r\n");
		}
    	
    }

    /**
     * 来电时，接听电话
     *
     * @throws Exception
     */
    public void answerThePhone() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFANSW\r\n");
		}else {
			ioWrite("AT#CE\r\n");
		}
    	
    }

    /**
     * 拨打电话
     *
     * @param phone
     * @throws Exception
     */
    public void call(String phone) throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFDIAL=" + phone + "\r\n");
		}else {
			ioWrite("AT#CW" + phone + "\r\n");
		}
    	
    }

    // 读取媒体状态 
    public void readMediaStatus() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+A2DPSTAT\r\n");
		}else {
			ioWrite("AT#MV\r\n");
		}
    	
    }
    // 读取媒体信息 
    public void readMediaInfo() throws Exception {
    	if (isRenGao) {
//    		ioWrite("AT+A2DPSTAT\r\n");
    	}else {
    		ioWrite("AT#MK\r\n");
    	}
    	
    }

    // end xudq

    /**
     * 暂停播放
     *
     * @throws Exception
     */
    public void pausePlaySync(boolean iPlay) throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+PP\r\n");
		}else {
			if (iPlay) {
				ioWrite("AT#MS\r\n");
			}else {
				ioWrite("AT#MB\r\n");
			}
		}
    	
    }
    /**
     * 强制暂停
     *
     * @throws Exception
     */
    public void pauseSync() throws Exception {
    	if (isRenGao) {
    	}else {
    		ioWrite("AT#MB\r\n");
    	}
    	
    }


    /**
     * 停止播放
     *
     * @throws Exception
     */
    public void stopPlay() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+STOP\r\n");
		}else {
			ioWrite("AT#MC\r\n");
		}
    	
    }

    /**
     * 播放下一首
     *
     * @throws Exception
     */
    public void playNext() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+FWD\r\n");
		}else {
			ioWrite("AT#MD\r\n");
		}
    	
    }

    /**
     * 播放上一首
     *
     * @throws Exception
     */
    public void playPrev() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+BACK\r\n");
		}else {
			ioWrite("AT#ME\r\n");
		}
    	
    }

   /**
    * 下载电话本 事件返回 PhonebookProtocol
    * @param type 1、电话本 2、来电记录 3、去电记录 4、未接来电 5、所有通话记录
    * @throws Exception 
    */
    public void downloadPhoneBookSync(int type) throws Exception {
		LogCat.d(TAG +"downloadPhoneBookSync---type="+type);
    	if (isRenGao) {
    		switch (type) {
    		case 1:
    		case 2:
    		case 3:
    		case 4:
    		case 5:
    			ioWrite("AT+PBDOWN="+type+"\r\n");
    			break;

    		default:
    			ioWrite("AT+PBDOWN=1\r\n");
    			break;
    		}
		}else {
			switch (type) {
			case 1:
				ioWrite("AT#PA\r\n");
				break;
			case 2:
				ioWrite("AT#PH\r\n");
				break;
			case 3:
				ioWrite("AT#PI\r\n");
				break;
			case 4:
				ioWrite("AT#PJ\r\n");
				break;
			case 5:
				ioWrite("AT#PX\r\n");
				break;

			default:
				ioWrite("AT#PA\r\n");
				break;
			}
		}
    	
    	
    }

    /**
     * 取消下载电话本
     *
     * @throws Exception
     */
    public void cancelDownloadPhoneBook() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+PBABORT\r\n");
		}else {
			ioWrite("AT#PS\r\n");
		}
    	
    }

    /**
     * 连接地址列表中的一个设备
     *
     * @param address
     * @throws Exception
     */
    public void connectDeviceSync(String address) throws Exception {
    	address = address.replace("\r\n", "");
    	if (isRenGao) {
    	        ioWrite("AT+CPDL=" + address + "\r\n");
		}else {
			ioWrite("AT#CC" + address + "\r\n");
		}
       
    }

    /**
     * 增加音量
     *
     * @throws Exception
     */
    public void incVolume() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+VUP\r\n");
		}else {
			ioWrite("AT#CK\r\n");
		}
    	
    }

    /**
     * 减少音量
     *
     * @throws Exception
     */
    public void decVolume() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+VDN\r\n");
		}else {
			ioWrite("AT#CL\r\n");
		}
    	
    }

    /**
     * 尝试下载电话本
     *
     * @throws Exception
     */
    public void tryToDownloadPhoneBook() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+PBCONN\r\n");
		}else {
			ioWrite("AT#PA\r\n");
		}
    }

    /**
     * 获取电话状态 事件返回 PhoneStatusProtocol
     *
     * @throws Exception
     */
    public void readPhoneStatusSync() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFPSTAT\r\n");
		}else {
			ioWrite("AT#CY\r\n");
		}
    	
    }

    /**
     * 删除配对列表中的某个设备 事件返回DeviceRemovedProtocol, PairingListProtocol
     *
     * @param address
     * @throws Exception
     */
    public void removeDevice(String address) throws Exception {
    	if (isRenGao) {
    		address = address.replace("\r\n", "");
    		 ioWrite("AT+DLPD=" + address + "\r\n");
		}else {
			ioWrite("AT#CV\r\n");//删除全部配对列表
		}
           
    }

    /**
     * 音频切换到手机
     *
     * @throws Exception
     */
    public void switchPhoneDevice() throws Exception {
    	if (isRenGao) {
    		 ioWrite("AT+HFADTS\r\n");
		}else {
			 ioWrite("AT#CO\r\n");
		}
           
    }

    /**
     * 音频切换到蓝牙
     *
     * @throws Exception
     */
    public void switchBtDevice() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFADTS\r\n");
		}else {
			 ioWrite("AT#CP\r\n");
		}
            
    }

    /**
     * 切换设备，转手机
     *
     * @throws Exception
     */
    public void switchDevice(boolean iPhone) throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFADTS\r\n");
		}else {
			 if(iPhone){
	                ioWrite("AT#CO\r\n");
	            }else{
	            	ioWrite("AT#CP\r\n");
	            }
		}
            
    }
    
    /**
     * 软重启
     *
     * @throws Exception
     */
    public void SetBoot() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+BOOT\r\n");
		}else {
			ioWrite("AT#CC\r\n");
		}
    	
    }
    /**
     * 软重启
     *
     * @throws Exception
     */
    public void setBTEnterACC(boolean isAccOff) throws Exception {
    	if (isRenGao) {
    	}else {
    		if (isAccOff) {
    			ioWrite("AT#CZ0\r\n");
			}else {
				ioWrite("AT#CZ1\r\n");
			}
    	}
    	
    }

    /**
     * 断开当前连接的设备
     *
     * @throws Exception
     */
    public void disconnectCurrentDevice() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+DSCA\r\n");
		}else {
			ioWrite("AT#CD\r\n");
		}
    }

    /**
     * 查询设备连接状态
     *
     * @throws Exception
     */
    public void queryPhoneStatus() throws Exception {
    	if (isRenGao) {
    		ioWrite("AT+HFPSTAT\r\n");
		}else {
			ioWrite("AT#CY\r\n");
		}
    }

    public void setDeviceName(String deviceName) throws Exception {
    	deviceName = deviceName.replace("\r\n", "");
    	if (isRenGao) {
    		ioWrite("AT+SLDN=" + deviceName + "\r\n");
		}else {
			ioWrite("AT#MM" + deviceName + "\r\n");
		}
    	UtilTools.setZlinkBTName(deviceName);
    }
    
    private void sendVirutalButton(EVirtualButton button, String prefix, String suffix) throws Exception {
        switch (button) {
            case ZERO:
                ioWrite(prefix + "0" + suffix);
                break;
            case ONE:
                ioWrite(prefix + "1" + suffix);
                break;
            case TWO:
                ioWrite(prefix + "2" + suffix);
                break;
            case THREE:
                ioWrite(prefix + "3" + suffix);
                break;
            case FOUR:
                ioWrite(prefix + "4" + suffix);
                break;
            case FIVE:
                ioWrite(prefix + "5" + suffix);
                break;
            case SIX:
                ioWrite(prefix + "6" + suffix);
                break;
            case SEVEN:
                ioWrite(prefix + "7" + suffix);
                break;
            case EIGHT:
                ioWrite(prefix + "8" + suffix);
                break;
            case NINE:
                ioWrite(prefix + "9" + suffix);
                break;
            case ASTERISK:
                ioWrite(prefix + "*" + suffix);
                break;
            case WELL:
                ioWrite(prefix + "#" + suffix);
                break;
        }
    }

    /**
     * 按虚拟按键
     *
     * @param button
     * @throws Exception
     */
    public void pressVirutalButton(EVirtualButton button) throws Exception {
    	if (isRenGao) {
    		sendVirutalButton(button, "AT+HFDTMF=", "\r\n");
		}else {
			sendVirutalButton(button, "AT#CX", "\r\n");
		}
    }
}
