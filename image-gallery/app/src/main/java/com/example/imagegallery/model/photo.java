package com.example.imagegallery.model;

public class photo {
    String id,owner,secret,server;
         int  farm;
    String  title;
  //  boolean ispublic,isfriend,isfamily;
    String url_s;
    int height_s,width_s;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

  /*  public boolean isIspublic() {
        return ispublic;
    }

    public boolean isIsfriend() {
        return isfriend;
    }

    public boolean isIsfamily() {
        return isfamily;
    }*/

    public String getUrl_s() {
        return url_s;
    }

    public int getHeight_s() {
        return height_s;
    }

    public int getWidth_s() {
        return width_s;
    }
}
