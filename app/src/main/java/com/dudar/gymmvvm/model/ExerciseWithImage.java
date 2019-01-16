package com.dudar.gymmvvm.model;

import java.util.List;

public class ExerciseWithImage {
    private Exercise ex;
    private List<ExerciseImage> img;

    public ExerciseWithImage(Exercise ex, List<ExerciseImage> img) {
        this.ex = ex;
        this.img = img;
    }

    public Exercise getEx() {
        return ex;
    }

    public List<ExerciseImage> getImg() {
        return img;
    }
}
