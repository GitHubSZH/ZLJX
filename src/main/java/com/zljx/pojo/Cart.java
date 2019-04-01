package com.zljx.pojo;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Cart {
    @Id
    private Long id;
    private String cart_name;
    private String cart_type;
    private Integer cart_size;
    private String picture;
    private Date created;
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
