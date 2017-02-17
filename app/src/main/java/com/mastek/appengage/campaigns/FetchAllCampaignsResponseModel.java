package com.mastek.appengage.campaigns;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pravin103082 on 09-02-2017.
 */

public class FetchAllCampaignsResponseModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("schedule_type")
    @Expose
    private String scheduleType;
    @SerializedName("recursive")
    @Expose
    private Boolean recursive;
    @SerializedName("trigger_time")
    @Expose
    private Integer triggerTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pn_title")
    @Expose
    private String pnTitle;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("creationDate")
    @Expose
    private Integer creationDate;
    @SerializedName("pn_msg")
    @Expose
    private String pnMsg;
    @SerializedName("endDate")
    @Expose
    private Integer endDate;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("startDate")
    @Expose
    private Integer startDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public Boolean getRecursive() {
        return recursive;
    }

    public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

    public Integer getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Integer triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnTitle() {
        return pnTitle;
    }

    public void setPnTitle(String pnTitle) {
        this.pnTitle = pnTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public String getPnMsg() {
        return pnMsg;
    }

    public void setPnMsg(String pnMsg) {
        this.pnMsg = pnMsg;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }
}
