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
public class SearchPins {

    private long pin_id;
    private long user_id;
    private long board_id;
    private long file_id;
    private SearchFile file;
    private int media_type;
    private String source;
    private String link;
    private String raw_text;
    private SearchText_meta text_meta;
    private int via;
    private int via_user_id;
    private String original;
    private long created_at;
    private int like_count;
    private int comment_count;
    private int repin_count;
    private int is_private;
    private String extra;
    private String orig_source;
    private SearchUser user;
    private SearchBoard board;
    private List<String> tags;
    public void setPin_id(long pin_id) {
         this.pin_id = pin_id;
     }
     public long getPin_id() {
         return pin_id;
     }

    public void setUser_id(long user_id) {
         this.user_id = user_id;
     }
     public long getUser_id() {
         return user_id;
     }

    public void setBoard_id(long board_id) {
         this.board_id = board_id;
     }
     public long getBoard_id() {
         return board_id;
     }

    public void setFile_id(long file_id) {
         this.file_id = file_id;
     }
     public long getFile_id() {
         return file_id;
     }

    public void setFile(SearchFile file) {
         this.file = file;
     }
     public SearchFile getFile() {
         return file;
     }

    public void setMedia_type(int media_type) {
         this.media_type = media_type;
     }
     public int getMedia_type() {
         return media_type;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setLink(String link) {
         this.link = link;
     }
     public String getLink() {
         return link;
     }

    public void setRaw_text(String raw_text) {
         this.raw_text = raw_text;
     }
     public String getRaw_text() {
         return raw_text;
     }

    public void setText_meta(SearchText_meta text_meta) {
         this.text_meta = text_meta;
     }
     public SearchText_meta getText_meta() {
         return text_meta;
     }

    public void setVia(int via) {
         this.via = via;
     }
     public int getVia() {
         return via;
     }

    public void setVia_user_id(int via_user_id) {
         this.via_user_id = via_user_id;
     }
     public int getVia_user_id() {
         return via_user_id;
     }

    public void setOriginal(String original) {
         this.original = original;
     }
     public String getOriginal() {
         return original;
     }

    public void setCreated_at(long created_at) {
         this.created_at = created_at;
     }
     public long getCreated_at() {
         return created_at;
     }

    public void setLike_count(int like_count) {
         this.like_count = like_count;
     }
     public int getLike_count() {
         return like_count;
     }

    public void setComment_count(int comment_count) {
         this.comment_count = comment_count;
     }
     public int getComment_count() {
         return comment_count;
     }

    public void setRepin_count(int repin_count) {
         this.repin_count = repin_count;
     }
     public int getRepin_count() {
         return repin_count;
     }

    public void setIs_private(int is_private) {
         this.is_private = is_private;
     }
     public int getIs_private() {
         return is_private;
     }

    public void setExtra(String extra) {
         this.extra = extra;
     }
     public String getExtra() {
         return extra;
     }

    public void setOrig_source(String orig_source) {
         this.orig_source = orig_source;
     }
     public String getOrig_source() {
         return orig_source;
     }

    public void setUser(SearchUser user) {
         this.user = user;
     }
     public SearchUser getUser() {
         return user;
     }

    public void setBoard(SearchBoard board) {
         this.board = board;
     }
     public SearchBoard getBoard() {
         return board;
     }

    public void setTags(List<String> tags) {
         this.tags = tags;
     }
     public List<String> getTags() {
         return tags;
     }

    @Override
    public String toString() {
        return "SearchPins{" +
                "pin_id=" + pin_id +
                ", user_id=" + user_id +
                ", board_id=" + board_id +
                ", file_id=" + file_id +
                ", file=" + file +
                ", media_type=" + media_type +
                ", source='" + source + '\'' +
                ", link='" + link + '\'' +
                ", raw_text='" + raw_text + '\'' +
                ", text_meta=" + text_meta +
                ", via=" + via +
                ", via_user_id=" + via_user_id +
                ", original='" + original + '\'' +
                ", created_at=" + created_at +
                ", like_count=" + like_count +
                ", comment_count=" + comment_count +
                ", repin_count=" + repin_count +
                ", is_private=" + is_private +
                ", extra='" + extra + '\'' +
                ", orig_source='" + orig_source + '\'' +
                ", user=" + user +
                ", board=" + board +
                ", tags=" + tags +
                '}';
    }
}