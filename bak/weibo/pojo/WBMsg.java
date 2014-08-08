package yy.weibo.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class WBMsg extends BaseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String screenName;
    private String uId;
    private String wbId;
    private String wbmId;
    private String text;
    private String picPath;
    private Date datetime;
    private Object[] others;

    public String getWbmId() {
        return wbmId;
    }

    public void setWbmId(String wbmId) {
        this.wbmId = wbmId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getWbId() {
        return wbId;
    }

    public void setWbId(String wbId) {
        this.wbId = wbId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Object[] getOthers() {
        return others;
    }

    public void setOthers(Object[] others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return "WBMsg [screenName=" + screenName + ", uId=" + uId + ", wbId=" + wbId + ", text=" + text + ", picPath="
                + picPath + ", datetime=" + datetime + ", others=" + Arrays.toString(others) + "]";
    }

}
