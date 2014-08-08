package yy.daily;

import java.util.List;

import yy.traffic.TrafficViolationQuery;
import yy.traffic.TrafficViolationVO;

public class CarSearchService {

    /**
     * @param args
     */
    public static void main(String[] args)throws Exception {
        TrafficViolationQuery tvq = new TrafficViolationQuery();
        String msg1 = tvq.queryViolation();
       // List<TrafficViolationVO> trafficViolationVOList = tvq.queryDetailViolationList("NYL723");
//        System.out.println(trafficViolationVOList);
//        System.out.println(msg1);
//        MsgCenter m = new MsgCenter();
//        WebContainer wc = m.logonFeiXin();
//        if (wc != null) {
//            m.sendMsg(wc, msg1+"|"+msg2);
//            m.logoutFeiXin(wc);
//        }
    }

}
