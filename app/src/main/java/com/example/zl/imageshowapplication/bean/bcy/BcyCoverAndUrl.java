package com.example.zl.imageshowapplication.bean.bcy;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * 相册封面和链接的类
 */
public class BcyCoverAndUrl {

	/**
	 * 相册封面
	 */
	private String albumCover;

	/**
	 * 相册链接
	 */
	private String albumUrl;

	public BcyCoverAndUrl(String albumCover, String albumUrl) {
		this.albumCover = albumCover;
		this.albumUrl = albumUrl;
	}

	public String getAlbumCover() {
		return albumCover;
	}

	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}

	public String getAlbumUrl() {
		return albumUrl;
	}

	public void setAlbumUrl(String albumUrl) {
		this.albumUrl = albumUrl;
	}

	@Override
	public String toString() {
		return "BcyCoverAndUrl [albumCover=" + albumCover + ", albumUrl=" + albumUrl + "]";
	}

}
