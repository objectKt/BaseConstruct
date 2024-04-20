package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 媒体信息协议
 * 
 * @author hknaruto
 * 
 */
public class MediaInfoProtocol extends BaseMultilineProtocol {
	private String title;
	private String artist;
	private String album;
	private int number;
	private int totalNumber;
	private String genre;
	private int playTime;

	public void setNumber(int number) {
		this.number = number;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	private boolean isAnalyzed = false;

	public MediaInfoProtocol() {

	}

	private void analyze() {
		for (Object o : super.getUnits()) {
			try {
				MediaInfoUnitProtocol unit = (MediaInfoUnitProtocol) o;
				byte[] playload = unit.getPlayload();
				String charset = getCharSet(playload);
				switch (playload[0]) {
				case 0x01:
					title = new String(playload, 3, playload.length - 3,
							charset);
					break;
				case 0x02:
					artist = new String(playload, 3, playload.length - 3,
							charset);
					break;
				case 0x03:
					album = new String(playload, 3, playload.length - 3,
							charset);
					break;
				case 0x04:
					String numberStr = new String(playload, 3,
							playload.length - 3, charset);
					number = Integer.parseInt(numberStr);
					break;
				case 0x05:
					String totalNumberStr = new String(playload, 3,
							playload.length - 3, charset);
					totalNumber = Integer.parseInt(totalNumberStr);
					break;
				case 0x06:
					genre = new String(playload, 3, playload.length - 3,
							charset);
					break;
				case 0x07:
					String playTimeStr = new String(playload, 3,
							playload.length - 3, charset);
					playTime = Integer.parseInt(playTimeStr);
					break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private String getCharSet(byte[] playload) {
		int code = playload[1] + (playload[2] << 8);
		switch (code) {
		case 3:
			return "ASCII";
		case 4:
			return "ISO-8859-1";
		case 15:
			return "JIS-X0201";
		case 17:
			return "SHIFT-JIS";
		case 36:
			return "KS-C-5601-1987";
		case 106:
			return "UTF-8";
		case 1000:
			return "UCS2";
		case 1013:
			return "UTF-16BE";
		case 2025:
			return "GB2312";
		case 2026:
			return "BIG5";
		}
		return null;
	}

	public String getTitle() {
		if (!isAnalyzed) {
			analyze();
		}
		return title;
	}

	public String getArtist() {
		if (!isAnalyzed) {
			analyze();
		}
		return artist;
	}

	public String getAlbum() {
		if (!isAnalyzed) {
			analyze();
		}
		return album;
	}

	public int getNumber() {
		if (!isAnalyzed) {
			analyze();
		}
		return number;
	}

	public int getTotalNumber() {
		if (!isAnalyzed) {
			analyze();
		}
		return totalNumber;
	}

	public String getGenre() {
		if (!isAnalyzed) {
			analyze();
		}
		return genre;
	}

	public int getPlayTime() {
		if (!isAnalyzed) {
			analyze();
		}
		return playTime;
	}
	
	public void setAnalyzed(boolean isAnalyzed){
		this.isAnalyzed = isAnalyzed;
	}
	
	public void setTitle(String name){
		this.title = name;
	}
	
	public void setArtist(String singer){
		this.artist = singer;
	}

	@Override
	public String toString() {
		return "MediaInfoProtocol{" +
				"title='" + title + '\'' +
				", artist='" + artist + '\'' +
				", album='" + album + '\'' +
				", number=" + number +
				", totalNumber=" + totalNumber +
				", genre='" + genre + '\'' +
				", playTime=" + playTime +
				", isAnalyzed=" + isAnalyzed +
				'}';
	}
}
