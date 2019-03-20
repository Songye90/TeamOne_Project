package cn.itcast.core.pojo.user;

import java.io.Serializable;

public class UserHot implements Serializable{
    private Integer id;
    private String onlinedate;
    private Integer onlinenum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOnlinedata() {
        return onlinedate;
    }

    public void setOnlinedata(String onlinedata) {
        this.onlinedate = onlinedata;
    }

    public Integer getOnlinenum() {
        return onlinenum;
    }

    public void setOnlinenum(Integer onlinenum) {
        this.onlinenum = onlinenum;
    }
}
