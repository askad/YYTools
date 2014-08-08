package yy.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import yy.http.Constant;

public class ResourceBundleUtil {
    private ResourceBundle resourceBundle;
    private static ResourceBundleUtil resourceBundleUtil;

    private ResourceBundleUtil() {
        resourceBundle = ResourceBundle.getBundle(Constant.CONFIG_PATH);
    }

    public static ResourceBundleUtil getInstance() {
        if (resourceBundleUtil == null) {
            resourceBundleUtil = new ResourceBundleUtil();
        }
        return resourceBundleUtil;
    }

    public String getStringUTF8(String key) throws Exception {
        String value = resourceBundle.getString(key);
        return value;// new String(value.getBytes(""),
                     // Constants.ENCODE_UTF8).toString();
    }

    public String getString(String key) throws Exception {
        String value = resourceBundle.getString(key);
        return value;
    }

    public void setProperties(String propertyName, String propertyValue) throws Exception {
        Properties p = new Properties();
        String filePath = getClass().getResource(Constant.CONFIG_FILE_PATH).getPath();
        InputStream in = new FileInputStream(filePath);
        p.load(in);
        in.close();
        p.setProperty(propertyName, propertyValue);// 设置属性值，如不属性不存在新建
        FileOutputStream out = new FileOutputStream(filePath);// 输出流
        p.store(out, null);// 设置属性头，如不想设置，请把后面一个用""替换掉
        out.flush();// 清空缓存，写入磁盘
        out.close();// 关闭输出流
        resourceBundle.clearCache();
    }
    public static void main(String[] args) throws Exception {
        ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();
       // resourceBundleUtil.setProperties(Constants.SINCE_ID, "123");
    }
}
