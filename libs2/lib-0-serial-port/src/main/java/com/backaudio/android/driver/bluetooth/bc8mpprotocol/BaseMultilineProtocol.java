package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

import java.util.ArrayList;
import java.util.List;

public class BaseMultilineProtocol {
	private List<Object> units;

	public BaseMultilineProtocol() {
		units = new ArrayList<Object>();
	}

	public void addUnit(Object unit) {
		if (!units.contains(unit)) {
			units.add(unit);
		}
	}

	/**
	 * 获取地址列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getUnits() {
		return units;
	}
}
