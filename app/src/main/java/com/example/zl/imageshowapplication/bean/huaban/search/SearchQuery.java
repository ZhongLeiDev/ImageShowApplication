/**
  * Copyright 2018 bejson.com 
  */
package com.example.zl.imageshowapplication.bean.huaban.search;

/**
 * Auto-generated: 2018-09-26 8:13:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SearchQuery {

    private String text;
    private String type;
    private String sort;
    private int page;
    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setSort(String sort) {
         this.sort = sort;
     }
     public String getSort() {
         return sort;
     }

    public void setPage(int page) {
         this.page = page;
     }
     public int getPage() {
         return page;
     }

    @Override
    public String toString() {
        return "SearchQuery{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", sort='" + sort + '\'' +
                ", page=" + page +
                '}';
    }
}