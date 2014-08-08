package yy.spider.workers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantParser {

    private final static String TAG_TEXT_JS = "<script.+?</script>";
    private final static String TAG_TEXT_CSS = "<style.+?</style>";
    private final static String TAG_TEXT_JAVA_COM = "<%--(.+?)--%>";
    private final static String TAG_TEXT_JAVA_COM2 = "<!--(.+?)-->";
    private final static String TAG_TEXT_JAVA = "<%(?!=)(.+?)%>";
    private final static String TAG_TEXT_JAVAO = "<%=(.+?)%>";
    private final static String TAG_TEXT_ALL = "<.+?>";
    private final static String TAG_TEXT_CSB = "class=\".*?\" >|class = '.*?' >|--%>|-->|[\\w]+\\s*=\\s*[\"'].*?[\"']|[-]+";
    private final static String TAG_TEXT_BLANK = "\\s|\\r\\n";
    private final static String TEXT_BLANK = "&nbsp;|&nbsp|\t";
    private final static String TEXT_BLANK_CR = "\r\n\\s*\r\n";
    private final static Pattern TAG_P_JAVA = Pattern.compile(TAG_TEXT_JAVA, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);
    private final static Pattern TAG_P_ALL = Pattern.compile(TAG_TEXT_ALL, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);
    private final static Pattern TAG_P_BLANK = Pattern.compile(TAG_TEXT_BLANK, Pattern.DOTALL
            | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern TAG_P_JS = Pattern.compile(TAG_TEXT_JS, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);
    private final static Pattern TAG_P_CSS = Pattern.compile(TAG_TEXT_CSS, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);
    private final static Pattern TAG_P_JAVAO = Pattern.compile(TAG_TEXT_JAVAO, Pattern.DOTALL
            | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern TAG_P_CSB = Pattern.compile(TAG_TEXT_CSB, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);
    private final static Pattern TAG_P_JAVA_COM = Pattern.compile(TAG_TEXT_JAVA_COM, Pattern.DOTALL
            | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern TAG_P_JAVA_COM2 = Pattern.compile(TAG_TEXT_JAVA_COM2, Pattern.DOTALL
            | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private final static Pattern TAG_P_BLANK_CR = Pattern.compile(TEXT_BLANK_CR, Pattern.DOTALL
            | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    public int count = 0;
    public int filecount = 0;
    public BufferedWriter bfw;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ConstantParser tp = new ConstantParser();
        tp.bfw = new BufferedWriter(new FileWriter("/temp/log"));
        tp.execFile(new File("src/main/webapp"));
        tp.bfw.close();
        System.out.println(tp.count);
        System.out.println(tp.filecount);

    }

    public void execFile(File fp) throws Exception {
        if (fp.isDirectory()) {
            for (File f : fp.listFiles()) {
                execFile(f);
            }
        } else {
            handleList(fp);
            filecount++;
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
        String pageContent1 = TAG_P_JS.matcher(sb.toString()).replaceAll("");// js
        String pageContent2 = TAG_P_CSS.matcher(pageContent1).replaceAll("");// css
        String pageContent3 = TAG_P_JAVA_COM.matcher(pageContent2).replaceAll("");// <%--
        pageContent3 = TAG_P_JAVA_COM2.matcher(pageContent3).replaceAll("");// <!--
        pageContent3 = TAG_P_JAVA.matcher(pageContent3).replaceAll("");// <%
        String pageContent4 = TAG_P_JAVAO.matcher(pageContent3).replaceAll("");// <%=
        String pageContent5 = TAG_P_ALL.matcher(pageContent4).replaceAll("");
        String pageContent6 = TAG_P_CSB.matcher(pageContent5).replaceAll("");
        pageContent6 = pageContent6.replaceAll(TEXT_BLANK, "");
        String pageContent8 = TAG_P_BLANK.matcher(pageContent6).replaceAll("");// /r/n
        if (!pageContent8.isEmpty()) {
            rewriteContent(f.getName().replaceAll(".jsp", ""), pageContent6);
            // new ConstantParser().writeFile(pageContent8, "C:/temp/",
            // f.getName());
            // System.out.println(f.getName());
            // break;
        }
        count++;
    }

    public void rewriteContent(String fname, String content) throws Exception {
        while (parserCheck(content)) {
            content = content.replaceAll(TEXT_BLANK_CR, "\r\n");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                bfw.write(fname + "." + line.trim() + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public boolean parserCheck(String pageContent) throws Exception {

        Matcher matherCP = TAG_P_BLANK_CR.matcher(pageContent);
        return matherCP.find();
    }
}
