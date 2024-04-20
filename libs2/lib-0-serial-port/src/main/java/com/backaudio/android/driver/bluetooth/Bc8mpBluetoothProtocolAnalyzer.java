package com.backaudio.android.driver.bluetooth;

import com.backaudio.android.driver.Utils;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.AnswerPhoneResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.BaseMultilineProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.CallOutResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.CallingOutProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.ConnectedDeviceProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceNameProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceRemovedProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceSwitchedProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.EnterPairingModeResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.HangUpPhoneResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.IncomingCallProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaInfoProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaInfoUnitProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaPlayStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PairingListProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PairingListUnitProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneBookCtrlStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneBookListProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.SetPlayStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.VersionProtocol;
import com.drake.logcat.LogCat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BC8MP蓝牙协议 请求协议格式：请求协议内容+\r\n 响应协议格式：\r\n+反馈协议内容+\r\n,对于通讯录类型的协议反馈，有固定头尾标记
 * 
 * @author hknaruto
 * @date 2014-1-15 下午3:37:18
 */
public class Bc8mpBluetoothProtocolAnalyzer implements
		IBluetoothProtocolAnalyzer {

	private static final String TAG = Bc8mpBluetoothProtocolAnalyzer.class.getSimpleName();
	private IBluetoothEventHandler handler = null;

	public void setEventHandler(IBluetoothEventHandler handler) {
		this.handler = handler;
	}

	private ByteArrayOutputStream protocolBuffer = new ByteArrayOutputStream();
	private boolean isLineStartDeteceted = false;
	private boolean isLineEndDetected = false;
	private byte nextExceptedValue = 0;

	private BaseMultilineProtocol mutilineProtocol = null;

	private MediaInfoProtocol mediaInfoProtocol = null;
	private MediaInfoUnitProtocol mediaInfoUnitProtocol = null;
	private PhoneBookListProtocol phoneBookListProtocol = null;
	private PhoneBookListProtocol phoneBookUnit = null;
	private static List<String> playStatusTag = null;

	public Bc8mpBluetoothProtocolAnalyzer() {

		playStatusTag = new ArrayList<String>();
		playStatusTag.add("PP");
		playStatusTag.add("STOP");
		playStatusTag.add("FWD");
		playStatusTag.add("BACK");
	}

	public void push(byte[] buffer, int off, int len) {
		byte[] newbuffer = Arrays.copyOf(buffer, len);
		LogCat.d(TAG + "bluetoothprot recv::"
				+ Utils.byteArrayToHexString(newbuffer, 0, newbuffer.length));
//		try {
//			Utils.saveLogLine(
//					Utils.byteArrayToHexString(newbuffer, 0, newbuffer.length),
//					true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		push(newbuffer);
	}

	/**
	 * 协议类型1：\r\n+GVER=V1.0.0\r\n 协议类型2： \r\n+LSPDSTART=2\r\n
	 * \r\n+LSPD=SDFFDSFDSFDSFD\r\n \r\n+LSPD=SDFFDSFDSFDSFD\r\n
	 * \r\n+LSPDEND\r\n
	 */
	public void push(byte[] buffer) {
		int i = 0;

		if (phoneBookUnit != null && phoneBookUnit.getNeedDataLength() > 0) {
			if (buffer.length <= phoneBookUnit.getNeedDataLength()) {
				phoneBookUnit.push(buffer, 0, buffer.length);
			} else {
				int endIndex = phoneBookUnit.getNeedDataLength();
				int remain = buffer.length - phoneBookUnit.getNeedDataLength();
				byte[] tmp = new byte[remain];
				phoneBookUnit
						.push(buffer, 0, phoneBookUnit.getNeedDataLength());
				for (int t = 0; t < remain; t++) {
					tmp[t] = buffer[t + endIndex];
				}
				push(tmp);
			}
			return;
		}

		// 直接读取二进制内容的协议/////////////////////////////////////////////
		if (mediaInfoUnitProtocol != null
				&& mediaInfoUnitProtocol.getNeedDataLength() > 0) {
			for (; i < buffer.length
					&& mediaInfoUnitProtocol.getNeedDataLength() > 0; i++) {
				mediaInfoUnitProtocol.push(buffer, i, 1);
			}
		}

		// 第一种类型协议解析
		for (; i < buffer.length; i++) {
			if (!isLineStartDeteceted) {
				if (buffer[i] == 0x0d) {
					isLineStartDeteceted = true;
					protocolBuffer.write(buffer, i, 1);
					nextExceptedValue = 0x0a;
				} else {
					isLineStartDeteceted = false;
					LogCat.d(TAG +"skip:"
							+ Integer.toHexString(buffer[i] > 0 ? buffer[i]
									: buffer[i] + 256));
					protocolBuffer.reset();
				}
			} else {
				// 行首标记找到，立刻匹配下一个字节
				if (nextExceptedValue != 0 && !isLineEndDetected) {
					if (nextExceptedValue == buffer[i]) {
						// 匹配到第二个标记
						protocolBuffer.write(buffer, i, 1);
						nextExceptedValue = 0;
					} else {
						// 数据不匹配，重置
						if (buffer[i] != 0x0d) {
							isLineStartDeteceted = false;
							LogCat.d(TAG +"skip:"
									+ Integer
											.toHexString(buffer[i] > 0 ? buffer[i]
													: buffer[i] + 256));
							protocolBuffer.reset();
						}
					}
				} else {
					// /r/n 头已经找到，接下来匹配 /r/n尾部
					if (!isLineEndDetected) {
						if (buffer[i] != 0x0d) {
							protocolBuffer.write(buffer, i, 1);
						} else {
							isLineEndDetected = true;
							protocolBuffer.write(buffer, i, 1);
							nextExceptedValue = 0x0a;
						}
					} else {
						if (nextExceptedValue == 0x0a) {
							protocolBuffer.write(buffer, i, 1);
							if (new String(protocolBuffer.toByteArray())
									.equals("\r\n\r\n")) {
								// 协议因为丢失数据导致鞋机出错，后面紧跟了有效协议，应道截取一段\r\n并继续生成协议
								LogCat.d(TAG +"0d0a0d0a error protocol,try to repare");
								protocolBuffer.reset();
								protocolBuffer.write(new byte[] { 0x0d, 0x0a },
										0, 2);
								isLineEndDetected = false;
								isLineStartDeteceted = true;
								nextExceptedValue = 0;
							} else {
								newLineProtocol(protocolBuffer.toByteArray());
								isLineStartDeteceted = false;
								isLineEndDetected = false;
								nextExceptedValue = 0;
								protocolBuffer.reset();

								// 成功解析出一条协议，剩余的数据递归调用
								int remain = buffer.length - i - 1;
								if (remain > 0) {
									byte[] tmp = new byte[remain];
									for (int rr = 0; rr < remain; rr++) {
										tmp[rr] = buffer[rr + i + 1];
									}
									push(tmp);
									return;
								}
							}
						} else {
							// 协议错误，重置
							LogCat.d(TAG +"protocol error, drop:"
									+ Integer.toHexString(buffer[i]));
							isLineStartDeteceted = false;
							isLineEndDetected = false;
							nextExceptedValue = 0;
							protocolBuffer.reset();
						}
					}
				}
			}
		}
	}

	/**
	 * 单行协议解析，内部识别出多行协议
	 * 
	 * @param byteArray
	 */
	private boolean newLineProtocol(byte[] byteArray) {
		String line = new String(byteArray);
		if (line == null || line.length() == 0) {
			return false;
		} else {
			if (!line.contains("HFPSTAT")) {
//				try {
//					Utils.saveLogLine(line, false);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}
		try {
			String key = null;
			String value = null;
			if (line.indexOf('=') != -1) {
				key = line.substring(line.indexOf("+") + 1, line.indexOf("="));
				value = line.substring(line.indexOf("=") + 1,
						line.lastIndexOf('\r'));
				LogCat.d(TAG +"bluetoothprotocal line recv::" + key + "="
						+ value);
			} else {
				key = line.substring(line.indexOf("+") + 1,
						line.lastIndexOf('\r'));
			}
			// 此代码放最前面,频率最高
			if (key.contains("PBDN")) {
				if (key.equals("PBDNDATA")) {
					if (phoneBookListProtocol == null) {
						phoneBookListProtocol = new PhoneBookListProtocol(key,
								value);
					}
					if (phoneBookUnit == null) {
						phoneBookUnit = new PhoneBookListProtocol(key, value);
					} else {
						if (phoneBookUnit.getNeedDataLength() == 0) {
							phoneBookListProtocol.addUnit(phoneBookUnit);
							phoneBookUnit = new PhoneBookListProtocol(key,
									value);
						} else {
							LogCat.d(TAG +"phonebook error,needData:"
									+ phoneBookUnit.getNeedDataLength());
						}
					}
					return true;
				}
				if (key.equals("PBDNEND")) {
					if (phoneBookListProtocol != null && phoneBookUnit != null) {
						if (phoneBookUnit.getNeedDataLength() != 0) {
							LogCat.d(TAG +"phonebook error,needData:"
									+ phoneBookUnit.getNeedDataLength());
						} else {
							phoneBookListProtocol.addUnit(phoneBookUnit);
							phoneBookUnit.reset();
						}
						handler.onPhoneBookList(phoneBookListProtocol);
					}
					phoneBookListProtocol = null;
					phoneBookUnit = null;
					return true;
				}
			}

			// 简单工厂生成协议单行协议/////////////////////////////////////////////////////
			if (key.equals("PREND")) {
				handler.onPairingModeEnd();
				return true;
			}
			if (key.equals("HFPOCID")) {
				CallingOutProtocol callingOutProtocol = new CallingOutProtocol(
						key, value);
				handler.onPhoneCallingOut(callingOutProtocol);
				return true;
			}
			if (key.equals("HFPAUDO")) {
				DeviceSwitchedProtocol deviceSwitchedProtocol = new DeviceSwitchedProtocol(
						key, value);
				handler.ondeviceSwitchedProtocol(deviceSwitchedProtocol);
				return true;
			}
			if (key.equals("GVER")) {
				VersionProtocol versionProtocol = new VersionProtocol(key,
						value);
				handler.onVersion(versionProtocol);
				return true;
			}

			if (key.equals("GLDN")) {
				DeviceNameProtocol deviceNameProtocol = new DeviceNameProtocol(
						key, value);
				handler.onDeviceName(deviceNameProtocol);
				return true;
			}

			if (key.equals("EPRM")) {
				EnterPairingModeResult enterPairingModeResult = new EnterPairingModeResult(
						key, value);
				handler.onPairingModeResult(enterPairingModeResult);
				return true;
			}

			if (playStatusTag.contains(key)) {
				SetPlayStatusProtocol playStatusProtocol = new SetPlayStatusProtocol(
						key, value);
				handler.onSetPlayStatus(playStatusProtocol);
				return true;
			}

			if (key.equals("HFPCLID")) {
				IncomingCallProtocol incomingCallProtocol = new IncomingCallProtocol(
						key, value);
				handler.onIncomingCall(incomingCallProtocol);
				return true;
			}

			if (key.equals("HFPSTAT")) {
				PhoneStatusProtocol phoneStatusProtocol = new PhoneStatusProtocol(
						key, value);
				handler.onPhoneStatus(phoneStatusProtocol);
				return true;
			}

			if (key.equals("HFANSW")) {
				AnswerPhoneResult answerPhoneResult = new AnswerPhoneResult(
						key, value);
				handler.onAnswerPhone(answerPhoneResult);
				return true;
			}

			if (key.equals("HFCHUP")) {
				HangUpPhoneResult hangUpPhoneResult = new HangUpPhoneResult(
						key, value);
				handler.onHangUpPhone(hangUpPhoneResult);
				return true;
			}

			if (key.equals("HFDIAL")) {
				CallOutResult callOutResult = new CallOutResult(key, value);
				handler.onCallOut(callOutResult);
				return true;
			}

			if (key.equals("AVRCPSTAT")) {
				MediaStatusProtocol mediaStatusProtocol = new MediaStatusProtocol(
						key, value);
				handler.onMediaStatus(mediaStatusProtocol);
				return true;
			}

			if (key.equals("A2DPSTAT") || key.equals("PLAYSTAT")) {
				MediaPlayStatusProtocol mediaPlayStatusProtocol = new MediaPlayStatusProtocol(
						key, value);
				handler.onMediaPlayStatus(mediaPlayStatusProtocol);
				return true;
			}

			if (key.equals("PBSTAT")) {
				PhoneBookCtrlStatusProtocol phoneBookCtrlStatusProtocol = new PhoneBookCtrlStatusProtocol(
						key, value);
				handler.onPhoneBookCtrlStatus(phoneBookCtrlStatusProtocol);
				return true;
			}

			if (key.equals("CCDA")) {
				ConnectedDeviceProtocol connectedDeviceProtocol = new ConnectedDeviceProtocol(
						key, value);
				handler.onConnectedDevice(connectedDeviceProtocol);
				return true;
			}

			if (key.equals("DLPD")) {
				DeviceRemovedProtocol deviceRemovedProtocol = new DeviceRemovedProtocol(
						key, value);
				handler.onDeviceRemoved(deviceRemovedProtocol);
				return true;
			}

			// //////////////////////////////////////////////////////////////////////////////////////
			// 一下是多行协议的分析
			if (key.contains("LSPD")) {
				// 匹配地址协议
				if (key.equals("LSPDSTART")) {
					mutilineProtocol = new BaseMultilineProtocol();
					return true;
				}
				if (key.equals("LSPD")) {
					if (mutilineProtocol != null) {
						PairingListUnitProtocol unit = new PairingListUnitProtocol(
								key, value);
						mutilineProtocol.addUnit(unit);
					}
					return true;
				}
				if (key.equals("LSPDEND")) {
					// 协议已经完成，抛出
					if (mutilineProtocol != null) {
						PairingListProtocol pairingListProtocol = new PairingListProtocol(
								mutilineProtocol);
						handler.onPairingList(pairingListProtocol);
						mutilineProtocol = null;
					}
				}
				return true;
			}

			if (key.contains("MEDIAINFO")) {
				if (key.equals("MEDIAINFOSTART")) {
					mediaInfoProtocol = new MediaInfoProtocol();
					return true;
				}
				if (key.equals("MEDIAINFOEND")) {
					if (mediaInfoProtocol != null) {
						handler.onMediaInfo(mediaInfoProtocol);
						mediaInfoProtocol = null;
						mediaInfoUnitProtocol = null;
					}
					return true;
				}
				if (key.equals("MEDIAINFO")) {
					mediaInfoUnitProtocol = new MediaInfoUnitProtocol(key,
							value);
					mediaInfoProtocol.addUnit(mediaInfoUnitProtocol);
					return true;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// LogCat.d(TAG +"cannot newProtocol:<" + new String(byteArray) + ">["
		// + Utils.byteArrayToHexString(byteArray) + "]");
		return false;
	}

	public void reset() {
	}

}
