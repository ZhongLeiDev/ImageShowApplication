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
public class SearchBoard {

    private long board_id;
    private long user_id;
    private String title;
    private String description;
    private String category_id;
    private int seq;
    private int pin_count;
    private int follow_count;
    private int like_count;
    private long created_at;
    private long updated_at;
    private int deleting;
    private int is_private;
    private Search_board_extra extra;
    public void setBoard_id(long board_id) {
         this.board_id = board_id;
     }
     public long getBoard_id() {
         return board_id;
     }

    public void setUser_id(long user_id) {
         this.user_id = user_id;
     }
     public long getUser_id() {
         return user_id;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setDescription(String description) {
         this.description = description;
     }
     public String getDescription() {
         return description;
     }

    public void setCategory_id(String category_id) {
         this.category_id = category_id;
     }
     public String getCategory_id() {
         return category_id;
     }

    public void setSeq(int seq) {
         this.seq = seq;
     }
     public int getSeq() {
         return seq;
     }

    public void setPin_count(int pin_count) {
         this.pin_count = pin_count;
     }
     public int getPin_count() {
         return pin_count;
     }

    public void setFollow_count(int follow_count) {
         this.follow_count = follow_count;
     }
     public int getFollow_count() {
         return follow_count;
     }

    public void setLike_count(int like_count) {
         this.like_count = like_count;
     }
     public int getLike_count() {
         return like_count;
     }

    public void setCreated_at(long created_at) {
         this.created_at = created_at;
     }
     public long getCreated_at() {
         return created_at;
     }

    public void setUpdated_at(long updated_at) {
         this.updated_at = updated_at;
     }
     public long getUpdated_at() {
         return updated_at;
     }

    public void setDeleting(int deleting) {
         this.deleting = deleting;
     }
     public int getDeleting() {
         return deleting;
     }

    public void setIs_private(int is_private) {
         this.is_private = is_private;
     }
     public int getIs_private() {
         return is_private;
     }

    public void setExtra(Search_board_extra extra) {
         this.extra = extra;
     }
     public Search_board_extra getExtra() {
         return extra;
     }

    @Override
    public String toString() {
        return "SearchBoard{" +
                "board_id=" + board_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category_id='" + category_id + '\'' +
                ", seq=" + seq +
                ", pin_count=" + pin_count +
                ", follow_count=" + follow_count +
                ", like_count=" + like_count +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleting=" + deleting +
                ", is_private=" + is_private +
                ", extra='" + extra + '\'' +
                '}';
    }
}