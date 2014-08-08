package yy.weibo.common;

import java.util.Calendar;
import java.util.Date;

import yy.common.ResourceBundleUtil;

public class DateUtil {
    /**
     * Get last hour.
     * Eg. Now is 2014/01/01 20:10:34, return 2014/01/01 19:10:34
     * @return
     * @throws Exception
     */
    public static Date getLastHourDate() throws Exception {
        ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();
        String endHour = resourceBundleUtil.getString(Constants.END_HOUR);
        int endInt = 0;
        Calendar cad = Calendar.getInstance();
        if (endHour == null || endHour.isEmpty()) {
            endInt = cad.get(Calendar.HOUR_OF_DAY);
        } else {
            endInt = Integer.parseInt(endHour);
        }
        //cad.set(Calendar.MINUTE, 0);
        //cad.set(Calendar.SECOND, 0);
        cad.set(Calendar.HOUR_OF_DAY, endInt-1);
        return cad.getTime();
    }

    public static void main(String[] args) throws Exception {
        //Calendar cad = Calendar.getInstance();
        System.out.println(DateUtil.getLastHourDate());//(Calendar.HOUR_OF_DAY));
    }
}
