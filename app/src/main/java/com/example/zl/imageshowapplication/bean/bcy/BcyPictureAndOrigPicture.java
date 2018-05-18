package com.example.zl.imageshowapplication.bean.bcy;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * 显示相片链接和原始相片链接集合，用于直接解析页面返回内容的方式
 */
public class BcyPictureAndOrigPicture {

	/**
	 * 显示相片链接
	 */
	private String pictureUrl;

	/**
	 * 原始相片链接
	 */
	private String origPictureUrl;

	public BcyPictureAndOrigPicture(String pictureUrl, String origPictureUrl) {
		this.pictureUrl = pictureUrl;
		this.origPictureUrl = origPictureUrl;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getOrigPictureUrl() {
		return origPictureUrl;
	}

	public void setOrigPictureUrl(String origPictureUrl) {
		this.origPictureUrl = origPictureUrl;
	}

	@Override
	public String toString() {
		return "BcyPictureAndOrigPicture [pictureUrl=" + pictureUrl + ", origPictureUrl=" + origPictureUrl + "]";
	}

}
