package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

public class Muscle{
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_front")
    private Boolean isFront;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
