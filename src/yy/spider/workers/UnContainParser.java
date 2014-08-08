package yy.spider.workers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class UnContainParser {

//    private final static String TAG_TEXT = "resourceBundleHandler";
    private final static String TAG_TEXT = "getHTMLOPTSWHSideLink";
    private final static String TAG_TEXT_2 = "getOptswchDesc";
    public BufferedWriter bfw;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        UnContainParser tp = new UnContainParser();
        tp.bfw = new BufferedWriter(new FileWriter("c:/temp/log"));
        tp.execFile(new File("/src/main/webapp"));
        tp.bfw.close();

    }

    public void execFile(File fp) throws Exception {
        if (fp.isDirectory()) {
            for (File f : fp.listFiles()) {
                execFile(f);
            }
        } else {
            handleList(fp);
        }
    }

    public void handleList(File f) throws Exception {

        if (f.getAbsolutePath().contains("\\chi\\")) {
            return;
        }
        if (!f.getName().endsWith(".jsp")) {
            return;
        }
        if (!f.getName().startsWith("S")) {
            return;
        }
        if (!f.getName().contains("Form")) {
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        String content = null;
        StringBuilder sb = new StringBuilder();
        while ((content = br.readLine()) != null) {
            sb.append(content);
            sb.append("\r\n");
        }
        br.close();
        String page = sb.toString();
        if (page.contains(TAG_TEXT_2)) {
            String preName = f.getName();
            bfw.write(preName + ":\r\n");
        }
    }
}
