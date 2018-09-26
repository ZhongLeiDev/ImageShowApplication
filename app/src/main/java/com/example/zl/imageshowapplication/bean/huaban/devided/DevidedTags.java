/**
  * Copyright 2018 bejson.com 
  */
package com.example.zl.imageshowapplication.bean.huaban.devided;
import java.util.List;

/**
 * Auto-generated: 2018-09-26 8:41:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class DevidedTags {

    private List<String> must;
    private List<String> should;
    public void setMust(List<String> must) {
         this.must = must;
     }
     public List<String> getMust() {
         return must;
     }

    public void setShould(List<String> should) {
         this.should = should;
     }
     public List<String> getShould() {
         return should;
     }

    @Override
    public String toString() {
        return "DevidedTags{" +
                "must=" + must +
                ", should=" + should +
                '}';
    }
}