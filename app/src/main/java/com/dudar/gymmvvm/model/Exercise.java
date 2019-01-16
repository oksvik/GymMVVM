package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Exercise{

    @SerializedName("id")
    private Integer id;
    @SerializedName("status")
    private String status;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("name_original")
    private String nameOriginal;
    @SerializedName("creation_date")
    private String creationDate;
    @SerializedName("category")
    private Integer categoryId;
    @SerializedName("language")
    private Integer language;
    @SerializedName("muscles")
    private List<Integer> muscles = null;
    @SerializedName("muscles_secondary")
    private List<Integer> musclesSecondary = null;
    @SerializedName("equipment")
    private List<Integer> equipment = null;

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public List<Integer> getMuscles() {
        return muscles;
    }

    public List<Integer> getMusclesSecondary() {
        return musclesSecondary;
    }

    public List<Integer> getEquipment() {
        return equipment;
    }
}
