package com.example.lennont.gankclientdemo.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LennonT on 2016/7/7.
 */
public class Image extends RealmObject {

    /**补充数据*/
    private int width = 0;
    private int height = 0;
    private int position = 0;

    @PrimaryKey
    private String _id;

    private String who;
    private String publishedAt;
    private String desc;
    private String type;
    private String url;
    private boolean used;

    private String createdAt;
    private String _ns;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String get_ns() {
        return _ns;
    }

    public void set_ns(String _ns) {
        this._ns = _ns;
    }

    public static void updateValue(Image image, Goods goods) {
        image.set_id(goods.get_id());
        image.setWho(goods.getWho());
        image.setPublishedAt(goods.getPublishedAt());
        image.setDesc(goods.getDesc());
        image.setType(goods.getType());
        image.setUrl(goods.getUrl());
        image.setUsed(goods.isUsed());
        image.setCreatedAt(goods.getCreatedAt());
        image.set_ns(goods.getSource());
    }
}
