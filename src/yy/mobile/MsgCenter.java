package yy.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import yy.common.Logger;
import yy.common.ResourceBundleUtil;
import yy.http.WebContainer;

public class MsgCenter {

    private ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();
    private Logger logger = new Logger(MsgCenter.class);
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        MsgCenter m = new MsgCenter();
        WebContainer wc = m.logonFeiXin();
        if (wc != null) {
            m.sendMsg(wc, "test");
            m.logoutFeiXin(wc);
        }
    }

    public WebContainer logonFeiXin() throws Exception {

        WebContainer wc = new WebContainer("UTF-8");
        Map<String, String> params = new HashMap<String, String>();

        params.put("m", resourceBundleUtil.getStringUTF8(MobileConstans.MOBILE_NO));
        params.put("pass", resourceBundleUtil.getStringUTF8(MobileConstans.MOBILE_PASS));
        params.put("captchaCode", "");
        params.put("checkCodeKey", null);
        params.put("t", new Long((new Date().getTime())).toString());

        String logonPage = wc.logonSession(MobileConstans.MOBILE_LOGON_URL, params);
        if (validateLogon(logonPage)) {
            logger.log("logon success");
            return wc;
        }
        wc.shutDownCon();
        return null;
    }

    public void logoutFeiXin(WebContainer wc) throws Exception {
        wc.setReferUrl(MobileConstans.MOBILE_REF_URL);
        wc.postRequest(MobileConstans.MOBILE_LOUT_URL, null);
        wc.shutDownCon();
    }

    public void sendMsg(WebContainer wc, String msg) throws Exception {
        wc.setReferUrl(MobileConstans.MOBILE_REF_URL);
        Map<String, String> paramsPost = new HashMap<String, String>();
        paramsPost.put(MobileConstans.MOBILE_MSG, msg);
        paramsPost.put(MobileConstans.MOBILE_TUSER, resourceBundleUtil.getStringUTF8(MobileConstans.MOBILE_UID));

        // 然后再第二次请求普通的url即可。
        String page = wc.postRequest(MobileConstans.MOBILE_MSG_URL, paramsPost);
        if (validateSend(page)) {
            logger.log("发送成功");
        }

    }

    private boolean validateSend(String page) {
        if ((page != null) && (page.indexOf("成功") >= 0)) {
            return true;
        }
        return false;
    }

    private boolean validateLogon(String page) {
        if ((page != null) && (page.indexOf("错误") >= 0)) {
            return false;
        }
        return true;
    }

}
