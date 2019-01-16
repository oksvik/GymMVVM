package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExerciseInfo {

    @SerializedName("id")
    private Integer id;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private ExerciseCategory category;
    @SerializedName("muscles")
    private List<Muscle> muscles = null;
    @SerializedName("muscles_secondary")
    private List<Muscle> musclesSecondary = null;
    @SerializedName("equipment")
    private List<Equipment> equipment = null;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ExerciseCategory getCategory() {
        return category;
    }

    public List<Muscle> getMuscles() {
        return muscles;
    }

    public List<Muscle> getMusclesSecondary() {
        return musclesSecondary;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }
}
