package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EquipmentResponse extends BaseResponse {

    @SerializedName("results")
    private List<Equipment> equipmentList = null;

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }
}
