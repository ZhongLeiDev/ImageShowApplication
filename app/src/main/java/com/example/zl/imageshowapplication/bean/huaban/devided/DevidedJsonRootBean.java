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
public class DevidedJsonRootBean {

    private List<DevidedPins> pins;
    private List<DevidedExplores> explores;
    public void setPins(List<DevidedPins> pins) {
         this.pins = pins;
     }
     public List<DevidedPins> getPins() {
         return pins;
     }

    public void setExplores(List<DevidedExplores> explores) {
         this.explores = explores;
     }
     public List<DevidedExplores> getExplores() {
         return explores;
     }

    @Override
    public String toString() {
        return "DevidedJsonRootBean{" +
                "pins=" + pins +
                ", explores=" + explores +
                '}';
    }
}