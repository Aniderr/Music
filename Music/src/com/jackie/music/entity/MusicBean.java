/**
 * 
 */
package com.jackie.music.entity;

/**
 * �����ļ���Ϣʵ�����
 * @author Jackie
 *
 */
public class MusicBean {

	//������id
	private int _id;
	
	//��������
	private String tilte ;
	
	//��������ר��
	private String album;
	
	//��������
	private String artist;
	
	//�����ļ�·��
	private String url;
	
	//�����ļ������ƣ����ﲻ�Ǹ�����
	private String display_name;
	
	//������������
	private String year;
	
	//�������ܲ���ʱ��
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
