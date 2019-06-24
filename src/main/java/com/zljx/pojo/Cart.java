package com.zljx.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {
    private Long id;
    private String cartName;
    private String cartType;
    private Integer cartSize;
        private String picture;
    private String cartDesc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    private Date created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public Integer getCartSize() {
        return cartSize;
    }

    public void setCartSize(Integer cartSize) {
        this.cartSize = cartSize;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCartDesc() {
        return cartDesc;
    }

    public void setCartDesc(String cartDesc) {
        this.cartDesc = cartDesc;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cartName='" + cartName + '\'' +
                ", cartType='" + cartType + '\'' +
                ", cartSize=" + cartSize +
                ", picture='" + picture + '\'' +
                ", cartDesc='" + cartDesc + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
