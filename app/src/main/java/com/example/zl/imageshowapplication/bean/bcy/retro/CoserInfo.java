package com.example.zl.imageshowapplication.bean.bcy.retro;

import java.io.Serializable;

/**
 * Coser信息类
 */
public class CoserInfo implements Serializable{

    private String coserId;

    private String coserName;

    private String coserPassword;

    private String coserAddress;

    private String coserAvatar;

    private String coserDescription;

    private String coserHomepage;

    public String getCoserId() {
        return coserId;
    }

    public void setCoserId(String coserId) {
        this.coserId = coserId;
    }

    public String getCoserName() {
        return coserName;
    }

    public void setCoserName(String coserName) {
        this.coserName = coserName;
    }

    public String getCoserPassword() {
        return coserPassword;
    }

    public void setCoserPassword(String coserPassword) {
        this.coserPassword = coserPassword;
    }

    public String getCoserAddress() {
        return coserAddress;
    }

    public void setCoserAddress(String coserAddress) {
        this.coserAddress = coserAddress;
    }

    public String getCoserAvatar() {
        return coserAvatar;
    }

    public void setCoserAvatar(String coserAvatar) {
        this.coserAvatar = coserAvatar;
    }

    public String getCoserDescription() {
        return coserDescription;
    }

    public void setCoserDescription(String coserDescription) {
        this.coserDescription = coserDescription;
    }

    public String getCoserHomepage() {
        return coserHomepage;
    }

    public void setCoserHomepage(String coserHomepage) {
        this.coserHomepage = coserHomepage;
    }

    @Override
    public String toString() {
        return "CoserInfo{" +
                "coserId='" + coserId + '\'' +
                ", coserName='" + coserName + '\'' +
                ", coserPassword='" + coserPassword + '\'' +
                ", coserAddress='" + coserAddress + '\'' +
                ", coserAvatar='" + coserAvatar + '\'' +
                ", coserDescription='" + coserDescription + '\'' +
                ", coserHomepage='" + coserHomepage + '\'' +
                '}';
    }
}
