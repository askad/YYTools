package yy.weibo;

import java.util.LinkedList;
import java.util.List;

import yy.common.ResourceBundleUtil;
import yy.weibo.common.Constants;
import yy.weibo.pojo.WBMsg;

public class LexicalRocker {
    private ResourceBundleUtil resourceBundleUtil = ResourceBundleUtil.getInstance();
    private String[] keyWordsArray;

    public LexicalRocker() throws Exception {
        String keyWords = resourceBundleUtil.getStringUTF8(Constants.TF_KEYWORDS);
        keyWordsArray = keyWords.split(Constants.TF_KEYWORDS_COMMA);
    }

    public List<WBMsg> fillterWBMsg(List<WBMsg> oriWBList) throws Exception {
        if (keyWordsArray == null) {
            return oriWBList;
        }
        List<WBMsg> resultWBList = new LinkedList<WBMsg>();
        for (WBMsg wb : oriWBList) {
            if (keywordsFillter(wb)) {
                resultWBList.add(wb);
            }
        }
        return resultWBList;
    }

    private boolean keywordsFillter(WBMsg wb) throws Exception {
        String text = wb.getText();
        for (String keyword : keyWordsArray) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
