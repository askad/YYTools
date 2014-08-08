package yy.traffic;

import java.util.regex.Pattern;

public class TrafficConstant {

    public final static String ALL_PARA_WZ="<input type=\"hidden\" name=\"HPHM\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"HPZL\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFSJ\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFDD\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFDZ\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFXW\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"qrydept\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"CJJG\" value=\"(.*?)\">";
    public final static Pattern PATTERN_WZ = Pattern.compile(ALL_PARA_WZ,Pattern.DOTALL);
    public final static Pattern PATTERN_ERORMSG = Pattern.compile("您查询的<font color=\"#FF0000\">.*?</font>共有<font color=red>(.*?)</font>条违法记录");


    public final static String RESOUSE_USERNAME="username";
    public final static String RESOUSE_USERPASS="password";
    public final static String URL_LOGIN="http://www.tjits.cn/login.asp";
    public final static String URL_QUERY_REF="http://www.tjits.cn/zxjdc.asp";
    public final static String URL_QUERY="http://www.tjits.cn/wfcx/vehiclelist.asp";

}
