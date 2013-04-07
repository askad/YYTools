package yy.traffic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import yy.common.Logger;
import yy.common.ResourceBundleUtil;
import yy.http.Constant;
import yy.http.WebContainer;

public class TrafficViolationQuery {

    private Logger logger = new Logger(TrafficViolationQuery.class);
    /**
     * 查询违章明细
     *
     * @throws Exception
     */
    public String queryDetailViolation() throws Exception {
        WebContainer wc = new WebContainer("GBK");
        Map<String, String> params = new HashMap<String, String>();
        ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();
        params.put("username", resourceBundleUtil.getStringUTF8("username"));
        params.put("password",  resourceBundleUtil.getStringUTF8("password"));
        String logonPage = wc.logonSession("http://www.tjits.cn/login.asp", params);
        if (validateLogon(logonPage)) {
            wc.setReferUrl("http://www.tjits.cn/zxjdc.asp");
            Map<String, String> paramsPost = new HashMap<String, String>();
            paramsPost.put("lei", "小型汽车");
            paramsPost.put("provice", "津");
            paramsPost.put("txtVehicleNo", resourceBundleUtil.getStringUTF8("WZPZNO"));
            paramsPost.put("Submit", "查询");

            // 然后再第二次请求普通的url即可。
            String page = wc.postRequest("http://www.tjits.cn/wfcx/vehiclelist.asp", paramsPost);
            List<TrafficViolationVO> trafficViolationVOList = parseDetailPageInfo(page);
            logger.log(trafficViolationVOList);
            StringBuilder sb = new StringBuilder();
            for(TrafficViolationVO trafficViolationVO:trafficViolationVOList){
                sb.append(makeWords(trafficViolationVO));
                sb.append("\r\n");
            }
            return sb.toString();
        }
        wc.shutDownCon();
        return "未找到违章记录！";
    }

    private String makeWords(TrafficViolationVO trafficViolationVO){
         StringBuilder sb = new StringBuilder("牌照号：");
         sb.append(trafficViolationVO.getHphm());
         sb.append("于");
         sb.append(trafficViolationVO.getWfsj());
         sb.append(",在");
         sb.append(trafficViolationVO.getWfdz());
         sb.append("违反规定：");
         sb.append(trafficViolationVO.getWfxw());
         sb.append("。处罚单位：");
         sb.append(trafficViolationVO.getCjjg());
         return sb.toString();
    }

    /**
     * 查询违章
     *
     * @throws Exception
     */
    public String queryViolation() throws Exception {
        WebContainer wc = new WebContainer("GBK");
        wc.setReferUrl("http://www.tjits.cn/wfcx/index.asp");
        Map<String, String> paramsPost = new HashMap<String, String>();
        ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();
        paramsPost.put("lei", "小型汽车");
        paramsPost.put("provice", "津");
        paramsPost.put("txtVehicleNo", resourceBundleUtil.getStringUTF8("WZPZNO"));

        // 然后再第二次请求普通的url即可。
        String page = wc.postRequest("http://www.tjits.cn/wfcx/vehiclelist.asp", paramsPost);
       // System.out.println(page);
        String msg = parsePageInfo(page);
        logger.log(msg);
        return msg;
    }

    private boolean validateLogon(String page) {
        if ((page != null) && (page.indexOf("登录成功") >= 0)) {
            return true;
        }
        return false;
    }

    public String parsePageInfo(String page) {
        Matcher matcherMsg = Constant.PATTERN_ERORMSG.matcher(page);
        if (matcherMsg.find()) {
            return "目前违章记录总数为：" + matcherMsg.group(1);
        }
        return "";
    }

    private List<TrafficViolationVO> parseDetailPageInfo(String page) {

        List<TrafficViolationVO> trafficViolationVOList = new ArrayList<TrafficViolationVO>();
        Matcher matherWZ = Constant.PATTERN_WZ.matcher(page);

        while (matherWZ.find()) {
            TrafficViolationVO trafficViolationVO = new TrafficViolationVO();
            trafficViolationVO.setHphm(matherWZ.group(1));
            trafficViolationVO.setHpzl(matherWZ.group(2));
            trafficViolationVO.setWfsj(matherWZ.group(3));
            trafficViolationVO.setWfdd(matherWZ.group(4));
            trafficViolationVO.setWfdz(matherWZ.group(5));
            trafficViolationVO.setWfxw(matherWZ.group(6));
            trafficViolationVO.setQrydept(matherWZ.group(7));
            trafficViolationVO.setCjjg(matherWZ.group(8));
            trafficViolationVOList.add(trafficViolationVO);
        }

        return trafficViolationVOList;
    }

    public static void main(String[] args) throws Exception {
        TrafficViolationQuery tvq = new TrafficViolationQuery();
//        File f = new File("d:/a.txt");
//        FileReader fr = new FileReader(f);
//        BufferedReader br = new BufferedReader(fr);
//        StringBuilder sb = new StringBuilder();
//        String c = null;
//        while ((c = br.readLine()) != null) {
//            sb.append(c + "\n");
//        }
//        br.close();
//        fr.close();
//        System.out.println(tvq.parseDetailPageInfo(sb.toString()));
  //      tvq.queryViolation();
        tvq.queryDetailViolation();
    //    System.out.println(tvq.parsePageInfo("您查询的<font color=\"#FF0000\">津NYL723</font>共有<font color=red>1</font>条违法记录"));
    }
}
