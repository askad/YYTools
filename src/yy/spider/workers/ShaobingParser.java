package yy.spider.workers;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import yy.common.db.MysqlConnection;
import yy.spider.PageParser;

import com.mysql.jdbc.PreparedStatement;

public class ShaobingParser implements PageParser {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
    }

    @Override
    public Object parser(String pageContent) throws Exception {
        //Document doc = Jsoup.parse(new File("C:\\yyang21\\am1.txt"), "utf-8");
        Document doc = Jsoup.parse(pageContent);
        Elements items = doc.select("li.s-result-item");
        List<AmazonItem> aiList = new ArrayList<AmazonItem>();
        for (Element item : items) {
            AmazonItem ai = new AmazonItem();
            ai.setName(item.select("a.s-access-detail-page").text());
            ai.setUrl(item.select("a").first().attr("href"));
            ai.setAsin(item.attr("data-asin"));
            Elements priceElme = item.select("span.s-price");
            ai.setPrice(new BigDecimal(priceElme.text().substring(1).replace(",", "")));
            // + ￥30.14 运费
            ai.setFee(new BigDecimal(priceElme.parents().get(1).select("span").get(2).text().substring(3)
                    .replace("运费", "").trim().replace(",", "")));
            aiList.add(ai);
        }
        System.out.println("Start insert.."+aiList.size());
        ShaoBingConn sbc = new ShaoBingConn();
        for(AmazonItem ai:aiList){
            sbc.insertBatchData(ai);
        }
        sbc.closeBatch();
        return null;
    }

    private boolean checkData(String title) {
        return true;
    }

    @Override
    public Object preParser(String pageContent) throws Exception {
        return null;
    }

    @Override
    public Object afterParser(Object pageContent) throws Exception {
        return null;
    }

}

class ShaoBingConn extends MysqlConnection {

    public ShaoBingConn() throws Exception {
        super("jdbc:mysql://localhost:4869/dailyinfo?useUnicode=true&characterEncoding=utf-8", "root", "qqqqqq");
    }

    private String INSERT_SQL = "INSERT INTO `dailyinfo`.`amazon_shopitem` (`name`, `link`, `price`, `fee`, `asin`) VALUES (?, ?, ?, ?, ?)";

    @Override
    protected void setValue(PreparedStatement statement, Object obj) throws SQLException {
        AmazonItem ai = (AmazonItem)obj;
        statement.setString(1, ai.getName());
        statement.setString(2, ai.getUrl());
        statement.setBigDecimal(3, ai.getPrice());
        statement.setBigDecimal(4, ai.getFee());
        statement.setString(5, ai.getAsin());
    }
    
    @Override
    protected String getSql(){
        return INSERT_SQL;
    }
    
}

class AmazonItem {
    private String name;
    private BigDecimal price;
    private BigDecimal fee;
    private String url;
    private String asin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    @Override
    public String toString() {
        return "AmazonItem [name=" + name + ", price=" + price + ", fee=" + fee + ", url=" + url + ", asin=" + asin
                + "]";
    }
}