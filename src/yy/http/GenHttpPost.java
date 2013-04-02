package yy.http;

import org.apache.http.client.methods.HttpPost;

public class GenHttpPost extends HttpPost {

    public GenHttpPost(String url) {
        super(url);
        this.setHeaders(Constant.getFFHeader());
    }

}
