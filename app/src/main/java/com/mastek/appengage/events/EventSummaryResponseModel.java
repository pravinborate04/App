package com.mastek.appengage.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 09-02-2017.
 */

public class EventSummaryResponseModel
{
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("Total_Event_Count")
    @Expose
    private Integer totalEventCount;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getTotalEventCount() {
        return totalEventCount;
    }

    public void setTotalEventCount(Integer totalEventCount) {
        this.totalEventCount = totalEventCount;
    }

}
