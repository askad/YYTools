package yy.daily;

import yy.http.WebContainer;
import yy.mobile.MsgCenter;
import yy.traffic.TrafficViolationQuery;

public class CarSearchService {

    /**
     * @param args
     */
    public static void main(String[] args)throws Exception {
        TrafficViolationQuery tvq = new TrafficViolationQuery();
        String msg1 = tvq.queryViolation();
        String msg2 = tvq.queryDetailViolation();
        MsgCenter m = new MsgCenter();
        WebContainer wc = m.logonFeiXin();
        if (wc != null) {
            m.sendMsg(wc, msg1+"|"+msg2);
            m.logoutFeiXin(wc);
        }
    }

}
