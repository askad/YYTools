package yy.spider;

public interface PageParser {
    // before the common page, as parser the login page.
    public Object preParser(String pageContent)throws Exception;
    // split the content
    public Object afterParser(Object result)throws Exception;
    // read the page
    public Object parser(String pageContent)throws Exception;
}
