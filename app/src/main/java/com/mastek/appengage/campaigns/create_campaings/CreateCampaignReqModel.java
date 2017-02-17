package com.mastek.appengage.campaigns.create_campaings;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Pravin103082 on 16-02-2017.
 */

public class CreateCampaignReqModel implements Serializable
{

    /**
     * schedule_type : immediate
     * recursive : false
     * trigger_time : 201702161257
     * name : testing
     * pn_title : Sourabh
     * status : active
     * creationDate : 20170216
     * pn_msg : Sourabh
     * endDate : null
     * total : 0
     * query : {"lm":["samsung","motorola"]}
     */

    private String schedule_type;
    private boolean recursive;
    private long trigger_time;
    private String name;
    private String pn_title;
    private String status;
    private int creationDate;
    private String pn_msg;
    private Object endDate;
    private int total;
    private QueryBean query;

    public String getSchedule_type() {
        return schedule_type;
    }

    public void setSchedule_type(String schedule_type) {
        this.schedule_type = schedule_type;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public long getTrigger_time() {
        return trigger_time;
    }

    public void setTrigger_time(long trigger_time) {
        this.trigger_time = trigger_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPn_title() {
        return pn_title;
    }

    public void setPn_title(String pn_title) {
        this.pn_title = pn_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    public String getPn_msg() {
        return pn_msg;
    }

    public void setPn_msg(String pn_msg) {
        this.pn_msg = pn_msg;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public static class QueryBean implements Serializable{
        private List<String> lm;

        private List<String> lmod;

        private List<String> losv;
        private List<String> lav;

        private List<String> ldt;

        private List<String> lpf;

        public List<String> getLm() {
            return lm;
        }

        public void setLm(List<String> lm) {
            this.lm = lm;
        }

        public List<String> getLmod() {
            return lmod;
        }

        public void setLmod(List<String> lmod) {
            this.lmod = lmod;
        }

        public List<String> getLosv() {
            return losv;
        }

        public void setLosv(List<String> losv) {
            this.losv = losv;
        }

        public List<String> getLav() {
            return lav;
        }

        public void setLav(List<String> lav) {
            this.lav = lav;
        }

        public List<String> getLdt() {
            return ldt;
        }

        public void setLdt(List<String> ldt) {
            this.ldt = ldt;
        }

        public List<String> getLpf() {
            return lpf;
        }

        public void setLpf(List<String> lpf) {
            this.lpf = lpf;
        }
    }
}
