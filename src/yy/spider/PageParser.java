package yy.spider;

public interface PageParser {
    // before the common page, as parser the login page.
    public Object preParser(String pageContent)throws Exception;
    public Object afterParser(Object result)throws Exception;
    public Object parser(String pageContent)throws Exception;
}
