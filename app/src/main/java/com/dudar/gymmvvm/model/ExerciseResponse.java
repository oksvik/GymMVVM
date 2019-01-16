package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExerciseResponse extends BaseResponse {
    @SerializedName("results")
    private List<Exercise> exercisesList = null;

    public List<Exercise> getExercisesList() {
        return exercisesList;
    }
}
