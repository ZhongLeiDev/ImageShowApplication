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
public class SearchFixedAds {

    private int id;
    private String name;
    private String link;
    private SearchImage image;
    private int type;
    private String placement;
    private String category;
    private long startAt;
    private long endAt;
    private int position;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setLink(String link) {
         this.link = link;
     }
     public String getLink() {
         return link;
     }

    public void setImage(SearchImage image) {
         this.image = image;
     }
     public SearchImage getImage() {
         return image;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setPlacement(String placement) {
         this.placement = placement;
     }
     public String getPlacement() {
         return placement;
     }

    public void setCategory(String category) {
         this.category = category;
     }
     public String getCategory() {
         return category;
     }

    public void setStartAt(long startAt) {
         this.startAt = startAt;
     }
     public long getStartAt() {
         return startAt;
     }

    public void setEndAt(long endAt) {
         this.endAt = endAt;
     }
     public long getEndAt() {
         return endAt;
     }

    public void setPosition(int position) {
         this.position = position;
     }
     public int getPosition() {
         return position;
     }

    @Override
    public String toString() {
        return "SearchFixedAds{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", image=" + image +
                ", type=" + type +
                ", placement='" + placement + '\'' +
                ", category='" + category + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", position=" + position +
                '}';
    }
}