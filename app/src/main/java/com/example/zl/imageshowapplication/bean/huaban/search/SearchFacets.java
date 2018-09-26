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
public class SearchFacets {

    private SearchResults results;
    private int total;
    public void setResults(SearchResults results) {
         this.results = results;
     }
     public SearchResults getResults() {
         return results;
     }

    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    @Override
    public String toString() {
        return "SearchFacets{" +
                "results=" + results +
                ", total=" + total +
                '}';
    }
}