package yy.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

abstract public class FileToolsBase {

    private List<String> fileNameList = null;
    private boolean logFlag = false;

    public String readFileContent(File f) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        String content = null;
        while ((content = bfr.readLine()) != null) {
            sb.append(content);
            sb.append("\r\n");
        }
        bfr.close();
        return sb.toString();
    }

    public void scanForContent(File fd) throws Exception {
        if (fd.isDirectory()) {
            for (File f : fd.listFiles()) {
                scanForContent(f);
            }
        } else {
            if (checkFile(fd)) {
                this.handleContent(this.readFileContent(fd));
            }
        }
    }

    public void scanForName(File fd) throws Exception {
        if (fd.isDirectory()) {
            for (File f : fd.listFiles()) {
                scanForName(f);
            }
        } else {
            if (checkFile(fd)) {
                this.handleName(fd);
            }
        }
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void addFileName(String fileName) {
        if (logFlag) {
            if (fileNameList == null) {
                fileNameList = new ArrayList<String>();
            }
            fileNameList.add(fileName);
        }
    }

    protected String getSuffix(String name) {
        return "." + name.substring(name.lastIndexOf(".") + 1);
    }

    public boolean isLogFlag() {
        return logFlag;
    }

    public void setLogFlag(boolean logFlag) {
        this.logFlag = logFlag;
    }

    // for get content in the file 
    abstract protected boolean handleContent(String pageContent) throws Exception;

    abstract protected boolean handleName(File f) throws Exception;

    abstract protected boolean checkFile(File f);
}
