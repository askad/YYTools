package yy.spider.workers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicParser {

    private final static String TAG_TEXT_LIT = "(<%=smartHF.getHTMLVar[^>]*?sv\\.[^>]*?\"output_cell\"[^>]*?%>)";
    // private final static String TAG_TEXT_LIT =
    // "(<%=smartHF.getLit[^>]+?sv\\..+?%>)";
    private final static Pattern TAG_P_LIT = Pattern.compile(TAG_TEXT_LIT, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);

    public BufferedWriter bfw;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        DynamicParser tp = new DynamicParser();
        tp.bfw = new BufferedWriter(new FileWriter("/temp/log"));
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
        Matcher matherCP = TAG_P_LIT.matcher(sb.toString());
        boolean firstFlag = true;
        while (matherCP.find()) {
            String preName = f.getName().replaceAll(".jsp", "");
            if (firstFlag) {
                bfw.write(preName + ":\r\n");
                firstFlag = false;
            }
            String line = matherCP.group(1);
            if (!line.contains("COBOLHTMLFormatter")) {
                bfw.write(line);
                bfw.write("\r\n");
            }
        }
    }

    public void rewriteContent(String fname, String content) throws Exception {
    }

    public void writeFile(String content, String path, String fileName) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        FileWriter fw;
        try {
            fw = new FileWriter(path + fileName);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
