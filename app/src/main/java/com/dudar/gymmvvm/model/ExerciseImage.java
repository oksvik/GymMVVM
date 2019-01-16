package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

public class ExerciseImage {
    @SerializedName("id")
    private Integer id;
    @SerializedName("status")
    private String status;
    @SerializedName("image")
    private String image;
    @SerializedName("is_main")
    private Boolean isMain;
    @SerializedName("exercise")
    private Integer exercise;

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public Boolean getMain() {
        return isMain;
    }
}
