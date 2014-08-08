package yy.weibo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import yy.common.MysqlConnection;
import yy.weibo.pojo.BaseVo;
import yy.weibo.pojo.WBMsg;

public class TfWbInfoDao extends MysqlConnection {
    public TfWbInfoDao() throws Exception {
        super();
    }

    private final static String INSERT_SQL = "INSERT INTO `TF_WB_INFO` (`WBID`, `CTDATE`, `UID`, `TEXT`, `PICPATH`,`OTHERS`,`WBMID`,`SCRNAME`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String SELECT_SQL = "SELECT OTHERS FROM TF_WB_INFO WHERE ID=?";

    public void saveRecord(WBMsg wbMsg) throws Exception {
        insertData(INSERT_SQL, wbMsg);
    }

    public List<Object> selectRecord(WBMsg wbMsg) throws Exception {
        return selectDate(SELECT_SQL, wbMsg);
    }

    @Override
    protected void setInsertPS(PreparedStatement ps, BaseVo baseVo) throws Exception {
        WBMsg wbMsg = (WBMsg) baseVo;
        int i = 1;
        ps.setString(i++, wbMsg.getWbId());
        ps.setTimestamp(i++, new Timestamp(wbMsg.getDatetime().getTime()));
        ps.setString(i++, wbMsg.getuId());
        ps.setString(i++, wbMsg.getText());
        ps.setString(i++, wbMsg.getPicPath());
        ps.setObject(i++, wbMsg.getOthers());
        ps.setString(i++, wbMsg.getWbmId());
        ps.setString(i++, wbMsg.getScreenName());
    }

    @Override
    protected void setSelectPS(PreparedStatement ps, BaseVo baseVo) throws Exception {
        // WBMsg wbMsg = (WBMsg) baseVo;
        int i = 1;
        // TODO
        ps.setString(i++, "6");
    }

    @Override
    protected List<Object> getSelectData(ResultSet rs) throws Exception {
        List<Object> resultList = new LinkedList<Object>();
        while (rs.next()) {
            resultList.add(rs.getObject(1));
        }
        return resultList;
    };

    public static void main(String[] args) throws Exception {
        TfWbInfoDao dao = new TfWbInfoDao();
         WBMsg wbMsg = new WBMsg();

         wbMsg.setScreenName("screenName");
         wbMsg.setuId("uId");
         wbMsg.setWbId("wbId");
         wbMsg.setWbmId("wbmId");
         wbMsg.setText("text");
         wbMsg.setPicPath("picPath");
         wbMsg.setDatetime(new Date());
         wbMsg.setOthers(new Object[] { "1", "2" });
         dao.saveRecord(wbMsg);

        List<Object> result = dao.selectRecord(null);
        Object[] obj = (Object[]) result.get(0);
        System.out.println(obj[1]);
    }
}
