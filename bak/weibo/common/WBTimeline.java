package yy.weibo.common;

import java.util.Date;
import java.util.List;

import weibo4j.Timeline;
import weibo4j.model.Paging;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import yy.common.ResourceBundleUtil;

public class WBTimeline extends Timeline {
    private static final long serialVersionUID = 1L;
    private Timeline tm;
    private Paging p;
    private ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();

    public WBTimeline() throws Exception {
        String access_token = resourceBundleUtil.getString(Constants.TOKEN_TEST);
        tm = new Timeline();
        p = getNewPaging();
        tm.client.setToken(access_token);
    }

    /**
     * 根据最小时间获得数据
     */
    public StatusWapper getUserTimelineByName(String name) throws WeiboException {
        if (p != null) {
            return tm.getUserTimelineByName(name, p, 0, 0);
        } else {
            return tm.getUserTimelineByName(name);
        }
    }

    /**
     * 根据minId获得数据
     *
     * @param name
     * @return
     * @throws Exception
     */
    public StatusWapper getUserTimelineByNameMinId(String name) throws Exception {
        if (p != null) {
            String sinceId = resourceBundleUtil.getString(Constants.SINCE_ID);
            if (sinceId != null && !sinceId.isEmpty()) {
                p.setSinceId(new Long(sinceId));
                return getUserTimelineByName(name);
            }
        }
        throw new Exception("Can't find the since id");
    }

    /**
     * 仅在置顶微博日期小于首条微博事件时获得，其余忽略
     *
     * @param name
     * @return
     * @throws Exception
     */
    public String getUserTopIds(String name) throws Exception {
        if (p != null) {
            p.setPage(1);
            p.setCount(2);
        }
        StatusWapper status = getUserTimelineByName(name);
        refreshPage();
        List<Status> statusList = status.getStatuses();
        if (statusList.size() > 0) {
            Date topDate = statusList.get(0).getCreatedAt();
            Date firstDate = statusList.get(1).getCreatedAt();
            if (topDate.compareTo(firstDate) < 0) {
                String topId = statusList.get(0).getId();
                return topId;
            }
        }
        return null;
    }

    public void addPage() {
        if (p != null) {
            int pageNo = p.getPage();
            if (pageNo > 0) {
                p.setPage(++pageNo);
            }
        }
    }

    public void updateMinId(String minid) throws Exception {
        resourceBundleUtil.setProperties(Constants.SINCE_ID, minid);
    }

    public void setMinId(long minId) {
        if (p != null) {
            p.setSinceId(minId);
        }
    }

    public void refreshPage() {
        p = getNewPaging();
    }

    private Paging getNewPaging() {
        Paging tp = new Paging();
        tp.setCount(Constants.PAGE_DEFAULT_COUNT);
        tp.setPage(Constants.PAGE_DEFAULT);
        return tp;
    }

    public Timeline getTm() {
        return tm;
    }

    public void setTm(Timeline tm) {
        this.tm = tm;
    }

    public Paging getP() {
        return p;
    }

    public void setP(Paging p) {
        this.p = p;
    }
}
