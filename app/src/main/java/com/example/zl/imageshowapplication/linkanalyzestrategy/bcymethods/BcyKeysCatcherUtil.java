package com.example.zl.imageshowapplication.linkanalyzestrategy.bcymethods;

import com.example.zl.imageshowapplication.bean.bcy.BcyCoverAndUrl;
import com.example.zl.imageshowapplication.bean.bcy.BcyPictureAndOrigPicture;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZhongLeiDev on 2018/5/17.
 * BCY 资源分析工具类
 */
public class BcyKeysCatcherUtil {

	/**
	 * 获取 coser 名称
	 * @param src
	 * @return
	 */
	public static String getCoserName(String src) {
		String coserName = "none";
		Pattern pattern= Pattern.compile("title=\".{0,20}\">");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
	    	//System.out.println("coserName:" + matcher.group());
	    	coserName = matcher.group().split("\"")[1];
	    	return coserName;
	    }
		return coserName;
	}

	/**
	 * 获取 coser 地址
	 * @param src
	 * @return
	 */
	public static String getCoserAddress(String src) {
		String coserAddress = "none";
		Pattern pattern= Pattern.compile("<p class=\"text fz14 lh1d4 mb15\">.*</p>");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
//	    	System.out.println("coserAddress:" + matcher.group());
	    	coserAddress = matcher.group().split(">")[1].split("<")[0];
//	    	return coserAddress;
	    }
		return coserAddress;
	}

	/**
	 * 获取 coser 头像
	 * @param src
	 * @return
	 */
	public static String getCoserAvatar(String src) {
		String coserAvatar = "none";
		Pattern pattern= Pattern.compile("https://user.bcyimg.com.*/abig");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
//	    	System.out.println("coserAddress:" + matcher.group());
	    	coserAvatar = matcher.group();
//	    	return coserAddress;
	    }
		return coserAvatar;
	}

	/**
	 * 获取 coser 简介
	 * @param src
	 * @return
	 */
	public static String getCoserDescription(String src) {
		String coserDesc = "";
		Pattern pattern= Pattern.compile("<p class=\"text fz14 lh1d4 mb15\">.*</p>");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
//	    	System.out.println("coserAddress:" + matcher.group());
	    	coserDesc = matcher.group().split(">")[1].split("<")[0];
	    	return coserDesc;
	    }
		return coserDesc;
	}

	/**
	 * 获取 coser 主页
	 * @param src
	 * @return
	 */
	public static String getCoserHomePage(String src) {
		String coserHomePage = "";
		Pattern pattern= Pattern.compile("class=\"_avatar.*title");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
//	    	System.out.println("coserAddress:" + matcher.group());
	    	coserHomePage = "https://bcy.net" + matcher.group().split("\"")[3] + "/post/Cos";
	    	return coserHomePage;
	    }
		return coserHomePage;
	}

	/**
	 * 获取相册封面和链接
	 * @param src
	 * @return
	 */
	public static List<BcyCoverAndUrl> getAlbumCoverAndUrl(String src) {
		List<BcyCoverAndUrl> cvlist = new ArrayList<BcyCoverAndUrl>();
		Pattern pattern= Pattern.compile("href=\"/coser/detail/.*/>");
	    Matcher matcher = pattern.matcher(src);
	    String str = "";
	    while(matcher.find()) {
	    	str = matcher.group();
//	    	System.out.println("AlbumCoverAndUrl:" + str);
	    	cvlist.add(new BcyCoverAndUrl(str.split("\"")[7],"https://bcy.net" + str.split("\"")[1]));
	    }
	    cvlist.remove(cvlist.size()-1);
		return cvlist;
	}

	/**
	 * 获取相册作者
	 * @param src
	 * @return
	 */
	public static String getAlbumAuthor(String src) {
		return getCoserName(src);
	}

	/**
	 * 获取相册标签，多个标签用分号(;)隔开
	 * @param src
	 * @return
	 */
	public static String getAlbumTag(String src) {
		String albumTag = "";
		Pattern pattern= Pattern.compile("data-tag-name=.*>");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
	    	albumTag += matcher.group().split("\"")[1] + ";";
	    }
		return albumTag;
	}

	/**
	 * 获取相册名称
	 * @param src
	 * @return
	 */
	public static String getAlbumName(String src) {
		String albumName = "";
		Pattern pattern= Pattern.compile("class=\"js-post-title\">[\\s\\S]*</h1>");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
	    	albumName = matcher.group().split(">")[1].split("<")[0].replace("&lt;", "《").replace("&gt;", "》");
	    }
	    if (albumName.length()>2) {
	    	return albumName.substring(1, albumName.length()-1);//去除换行符
		} else {
			return "空";
		}
	}

	/**
	 * 获取相册简介
	 * @param src
	 * @return
	 */
	public static String getAlbumDescription(String src) {
		String albumDesc = "";
		Pattern pattern= Pattern.compile("js-content-img-wrap[\\s\\S]*<br/>");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
//	    	System.out.println("AlbumDesc: " + matcher.group().replace("<br>",""));
	    	albumDesc = matcher.group().replace("<br>","").split(">")[1].split("<")[0];
	    }
	    if (albumDesc.length()>2) {
	    	return albumDesc.substring(1, albumDesc.length()-1);
	    } else {
	    	return "空";
	    }
	}

	/**
	 * 获取作品总页数
	 * @param src
	 * @param pageSize	每页容量
	 * @return
	 */
	public static int getPageNum(String src, Double pageSize) {
		int pageNum = 0;
		Pattern pattern= Pattern.compile("共.*篇");
	    Matcher matcher = pattern.matcher(src);
	    while(matcher.find()) {
	    	double number = Double.parseDouble(matcher.group().replace("共", "").replace("篇", ""));
	    	System.out.println("共有" + number + "个相册");
	    	pageNum = (int)Math.ceil(number/pageSize);//向上取整
	    }
		return pageNum;
	}

	/**
	 * 获取显示图片链接和原始图片链接集合
	 * @param src
	 * @return
	 */
	public static List<BcyPictureAndOrigPicture> getPictureAndOrigPicture(String src) {
		List<BcyPictureAndOrigPicture> picList = new ArrayList<>();
		Pattern pattern= Pattern.compile("https://.{12,18}/coser/.{1,10}/post/.{1,10}/.{30,40}/w650");
	    Matcher matcher = pattern.matcher(src);
	    String str = "";
	    while(matcher.find()) {
	    	str = matcher.group();
//	    	System.out.println("UrlText: " + str);
	    	picList.add(new BcyPictureAndOrigPicture(str, str.replace("/w650", "")));
	    }
		return picList;
	}

}
