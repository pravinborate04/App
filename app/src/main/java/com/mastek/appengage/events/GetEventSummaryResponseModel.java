package com.mastek.appengage.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 09-02-2017.
 */

public class GetEventSummaryResponseModel
{
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("event_count")
    @Expose
    private Integer eventCount;
    @SerializedName("user_count")
    @Expose
    private Integer userCount;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

}
