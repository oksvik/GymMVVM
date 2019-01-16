package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageResponse extends BaseResponse{
    @SerializedName("results")
    private List<ExerciseImage> imageList = null;

    public List<ExerciseImage> getImageList() {
        return imageList;
    }
}
