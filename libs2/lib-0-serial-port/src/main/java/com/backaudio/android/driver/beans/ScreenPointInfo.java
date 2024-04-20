package com.backaudio.android.driver.beans;

public class ScreenPointInfo {
	private boolean isValid;
	private int x;
	private int y;
	/**
	 * 两指触摸时状态，0无效,1放大,2缩小,3左旋,4右旋
	 */
	private int state;
	private int step;//state 的步进
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScreenPointInfo [isValid=");
		builder.append(isValid);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", state=");
		builder.append(state);
		builder.append(", step=");
		builder.append(step);
		builder.append("]");
		return builder.toString();
	}
	
}
