/**
  * Copyright 2018 bejson.com 
  */
package com.example.zl.imageshowapplication.bean.huaban.search;
import java.util.List;

/**
 * Auto-generated: 2018-09-26 8:13:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SearchAds {

    private List<String> normalAds;
    private List<SearchFixedAds> fixedAds;
    public void setNormalAds(List<String> normalAds) {
         this.normalAds = normalAds;
     }
     public List<String> getNormalAds() {
         return normalAds;
     }

    public void setFixedAds(List<SearchFixedAds> fixedAds) {
         this.fixedAds = fixedAds;
     }
     public List<SearchFixedAds> getFixedAds() {
         return fixedAds;
     }

    @Override
    public String toString() {
        return "SearchAds{" +
                "normalAds=" + normalAds +
                ", fixedAds=" + fixedAds +
                '}';
    }
}