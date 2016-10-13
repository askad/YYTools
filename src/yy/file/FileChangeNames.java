package yy.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileChangeNames extends FileToolsBase {

    private final static String URL_TEXT = "";
    final static Pattern SFL_P = Pattern.compile(URL_TEXT, Pattern.CASE_INSENSITIVE);

    protected boolean handleContent(String pageContent) throws Exception {
//        Matcher matherCP = SFL_P.matcher(pageContent);
//        if (matherCP.find()) {
//            System.out.println(pageContent);
//        }
        return false;
    }

    protected boolean handleName(File f) throws Exception {
        long timer = f.lastModified();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("/yyyyMMdd_HHmmss");
        cal.setTimeInMillis(timer);
        f.renameTo(new File(f.getParent() + formatter.format(cal.getTime()) + getSuffix(f.getName())));
        return true;
    }

    protected boolean checkFile(File f) {
        return true;
    }

    public static void main(String[] s) throws Exception {
        File f = new File("C:/yyang21/Personal/MyProject/JAVA_WKSP/New folder/");
        FileChangeNames ft = new FileChangeNames();
        ft.setLogFlag(true);
        ft.scanForName(f);
        System.out.print("totals: " + ft.getFileNameList().size());
    }
}
