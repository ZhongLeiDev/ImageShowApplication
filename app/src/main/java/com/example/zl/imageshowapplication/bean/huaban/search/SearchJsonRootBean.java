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
public class SearchJsonRootBean {

    private SearchQuery query;
    private int pin_count;
    private int board_count;
    private int people_count;
    private SearchFacets facets;
    private List<SearchPins> pins;
    private int page;
    private SearchAds ads;
    private List<String> search_ads;
    private String target;
    public void setQuery(SearchQuery query) {
         this.query = query;
     }
     public SearchQuery getQuery() {
         return query;
     }

    public void setPin_count(int pin_count) {
         this.pin_count = pin_count;
     }
     public int getPin_count() {
         return pin_count;
     }

    public void setBoard_count(int board_count) {
         this.board_count = board_count;
     }
     public int getBoard_count() {
         return board_count;
     }

    public void setPeople_count(int people_count) {
         this.people_count = people_count;
     }
     public int getPeople_count() {
         return people_count;
     }

    public void setFacets(SearchFacets facets) {
         this.facets = facets;
     }
     public SearchFacets getFacets() {
         return facets;
     }

    public void setPins(List<SearchPins> pins) {
         this.pins = pins;
     }
     public List<SearchPins> getPins() {
         return pins;
     }

    public void setPage(int page) {
         this.page = page;
     }
     public int getPage() {
         return page;
     }

    public void setAds(SearchAds ads) {
         this.ads = ads;
     }
     public SearchAds getAds() {
         return ads;
     }

    public void setSearch_ads(List<String> search_ads) {
         this.search_ads = search_ads;
     }
     public List<String> getSearch_ads() {
         return search_ads;
     }

    public void setTarget(String target) {
         this.target = target;
     }
     public String getTarget() {
         return target;
     }

    @Override
    public String toString() {
        return "DevidedJsonRootBean{" +
                "query=" + query +
                ", pin_count=" + pin_count +
                ", board_count=" + board_count +
                ", people_count=" + people_count +
                ", facets=" + facets +
                ", pins=" + pins +
                ", page=" + page +
                ", ads=" + ads +
                ", search_ads=" + search_ads +
                ", target='" + target + '\'' +
                '}';
    }
}