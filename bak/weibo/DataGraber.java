package yy.weibo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import yy.weibo.common.Constants;
import yy.weibo.common.DateUtil;
import yy.weibo.common.WBTimeline;
import yy.weibo.parse.DataGraber;
import yy.weibo.pojo.WBMsg;

public class DataGraber {
    private Date endDate = null;
    private WBTimeline wbt;

    public DataGraber() throws Exception {
        wbt = new WBTimeline();
        endDate = DateUtil.getLastHourDate();
    }

    /**
     * 直接取到endDate之前的所有数据
     *
     * @param name
     * @return
     * @throws Exception
     */
    public List<WBMsg> getWBMsgByName(String name) throws Exception {
        List<WBMsg> wbMsgList = new LinkedList<WBMsg>();
        boolean flag = true;
        String topId = wbt.getUserTopIds(name);
        while (flag) {
            StatusWapper status = wbt.getUserTimelineByName(name);
            List<Status> statusList = status.getStatuses();
            fillterTopId(topId, statusList);
            // 未取到
            if (statusList.size() <= 0) {
                break;
            }
            for (Status ts : status.getStatuses()) {
                // 比规定时间HH:MM早则结束
                if (!checkNotEnd(ts)) {
                    flag = false;
                    break;
                }
                wbMsgList.add(changeStatusWB(ts));
            }
            // 一次数量阀值
            if (wbMsgList.size() > Constants.MAX_SIZE) {
                break;
            }
            wbt.addPage();
        }
        if (wbMsgList.size() > 0) {
            wbt.updateMinId(wbMsgList.get(0).getWbId());
        }
        return wbMsgList;
    }

    /**
     * 直接取到MinId之前的所有数据
     *
     * @param name
     * @return
     * @throws Exception
     */
    public List<WBMsg> getWBMsgByNameMinId(String name) throws Exception {
        List<WBMsg> wbMsgList = new LinkedList<WBMsg>();
        String topId = wbt.getUserTopIds(name);
        boolean flag = true;
        while (flag) {
            StatusWapper status = wbt.getUserTimelineByNameMinId(name);
            List<Status> statusList = status.getStatuses();
            int count = statusList.size();
            fillterTopId(topId, statusList);
            // 未取到
            if (statusList.size() <= 0) {
                break;
            }
            for (Status ts : status.getStatuses()) {
                // 比规定时间HH:MM早则结束
                if (!checkNotEnd(ts)) {
                    flag = false;
                    break;
                }
                wbMsgList.add(changeStatusWB(ts));
            }
            // 一次数量阀值
            if (wbMsgList.size() > Constants.MAX_SIZE) {
                break;
            }
            if (count < Constants.PAGE_DEFAULT_COUNT) {
                break;
            }
            wbt.addPage();
        }
        if (wbMsgList.size() > 0) {
            wbt.updateMinId(wbMsgList.get(wbMsgList.size() - 1).getWbId());
        }
        return wbMsgList;
    }

    /**
     * 过滤置顶微博
     * @param topId
     * @param statusList
     */
    private void fillterTopId(String topId, List<Status> statusList) {
        // 是置顶微博则删除
        if (topId != null && !topId.isEmpty() && statusList.size() > 0 && topId.equals(statusList.get(0).getId())) {
            statusList.remove(0);
        }
    }

    /**
     * 是否到末尾
     *
     * @param s
     * @return
     */
    private boolean checkNotEnd(Status s) {
        Date startDate = s.getCreatedAt();
        if (startDate.compareTo(endDate) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 微博对象转换成本地VO
     * @param s
     * @return
     */
    private WBMsg changeStatusWB(Status s) {
        WBMsg wbmsg = new WBMsg();
        wbmsg.setDatetime(s.getCreatedAt());
        String text = s.getText();
        if (s.getRetweetedStatus() != null) {
            text += " 转发自：";
            text += s.getRetweetedStatus().getText();
        }
        wbmsg.setText(text);

        wbmsg.setWbId(s.getId());
        wbmsg.setWbmId(s.getMid());
        wbmsg.setuId(s.getUser().getId());
        wbmsg.setScreenName(s.getUser().getScreenName());
        wbmsg.setPicPath(s.getOriginalPic());
        wbmsg.setOthers(new Object[] { s.getGeo(), s.getUser() });
        return wbmsg;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static void main(String[] args) throws Exception {
        DataGraber d = new DataGraber();
        System.out.println(d.getWBMsgByName("奏耐天津"));
        //System.out.println(d.getWBMsgByNameMinId("奏耐天津"));
    }
}
