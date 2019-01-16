package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MuscleResponse extends BaseResponse{
    @SerializedName("results")
    private List<Muscle> muscleList = null;

    public List<Muscle> getMuscleList() {
        return muscleList;
    }
}
