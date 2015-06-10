/**
 * 
 */
package com.jackie.music.entity;

/**
 * 歌曲文件信息实体对象
 * @author Jackie
 *
 */
public class MusicBean {

	//歌曲的id
	private int _id;
	
	//歌曲名称
	private String tilte ;
	
	//歌曲所属专辑
	private String album;
	
	//歌手名称
	private String artist;
	
	//歌曲文件路径
	private String url;
	
	//歌曲文件的名称，这里不是歌曲名
	private String display_name;
	
	//歌曲发行日期
	private String year;
	
	//歌曲的总播放时长
	private int duration;
	
	private int size;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
