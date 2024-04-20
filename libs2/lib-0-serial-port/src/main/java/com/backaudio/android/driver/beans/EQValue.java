package com.backaudio.android.driver.beans;

public class EQValue {

	public int bassValue = 10;
	public int midValue = 10;
	public int trebleValue = 10;
	public int bassHZ = 0;
	public int midHZ = 0;
	public int trebleHZ = 0;
	public int megaBassValue = 10;

	public void setdata(int[] data) {
		bassValue = data[0];
		midValue = data[1];
		trebleValue = data[2];
		bassHZ = data[3];
		midHZ = data[4];
		trebleHZ = data[5];
		megaBassValue = data[6];
	}

	public int[] getdata() {
		int[] data = { bassValue, midValue, trebleValue, bassHZ, midHZ,
				trebleHZ, megaBassValue };
		return data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EQValue [bassValue=");
		builder.append(bassValue);
		builder.append(", midValue=");
		builder.append(midValue);
		builder.append(", trebleValue=");
		builder.append(trebleValue);
		builder.append(", bassHZ=");
		builder.append(bassHZ);
		builder.append(", midHZ=");
		builder.append(midHZ);
		builder.append(", trebleHZ=");
		builder.append(trebleHZ);
		builder.append(", megaBassValue=");
		builder.append(megaBassValue);
		builder.append("]");
		return builder.toString();
	}
}
