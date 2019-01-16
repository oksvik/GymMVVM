package com.dudar.gymmvvm.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("count")
    private Integer count;
    @SerializedName("next")
    private String nextPage;
    @SerializedName("previous")
    private String previousPage;

    public String getNextPage() {
        return nextPage;
    }

}
