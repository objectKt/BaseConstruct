package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

import java.util.List;

/**
 * 配对类表
 * 
 * @author hknaruto
 * 
 */
public class PairingListProtocol {
	private List<PairingListUnitProtocol> units;

	@SuppressWarnings("unchecked")
	public PairingListProtocol(BaseMultilineProtocol mutilineProtocol) {
		try {
			units = (List<PairingListUnitProtocol>) mutilineProtocol.getUnits();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获取地址列表
	 * 
	 * @return
	 */
	public List<PairingListUnitProtocol> getUnits() {
		return units;
	}

}
