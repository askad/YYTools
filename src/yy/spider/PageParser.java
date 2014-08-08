package yy.spider;

public interface PageParser {
    public Object preParser(String pageContent)throws Exception;
    public Object afterParser(String pageContent)throws Exception;
    public Object parser(String pageContent)throws Exception;
}
