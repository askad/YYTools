package yy.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MD5Tools {

    private final static String[] DICT = { "chang", "wei", "da", "chu", "2007", "5", "29" };
    private final static String FILE_PATH = "d:/yy.tmp";
    private List<String> keyList;
    private int strLength = 0;
    private int startPos = 0;

    /**
     * 100w：14M
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MD5Tools md5tools = new MD5Tools();
        // Map<String, String> md5Map =
        // md5tools.processAndCreateMap();//754eff01681af766
        // md5tools.getOriByMD5(md5Map, "ed52c31385554fd4");

        LinkedList<String> wordList = new LinkedList<String>();
        // wordList.add("a");
        wordList.add("b");
        wordList.add("c");
        // wordList.add("a");
        // wordList.add("a");
        LinkedList<String> resultList = md5tools.getAllWords("a", wordList);
        for (String word : md5tools.getKeyList()) {
            System.out.println(word);
        }
        // File f = new File("d:/yy.tmp");
        // Map s = new HashMap();
        // for (int i = 1; i < 1000000; i++) {
        // s.put(i, "Changwei2007");
        // }
        // ObjectOutputStream fs = new ObjectOutputStream(new
        // FileOutputStream(f));
        // fs.writeObject(s);
        // fs.close();
    }

    public Map<String, String> processAndCreateMap() throws Exception {
        Map<String, String> md5Map = null;
        if (startPos > 0) {
            md5Map = (Map<String, String>) getResultFromFile();
        } else {
            md5Map = new TreeMap<String, String>();
            List<String> dicList = createList();
            // createWords(dicList, "", getStrLength());
            List<String> wordsList = getKeyList();
            for (String word : wordsList) {
                String md5Str = getMd5(word);
                if (md5Str == null) {
                    continue;
                }
                md5Map.put(word, md5Str);
            }
            // writeResult(md5Map);
        }
        return md5Map;
    }

    public void getOriByMD5(Map<String, String> md5Map, String md5Str) {
        Set keySet = md5Map.keySet();
        Object[] keyArray = keySet.toArray();
        for (int i = startPos; i < keyArray.length; i++) {
            if (md5Str.equals(md5Map.get((String) keyArray[i]))) {
                System.out.println(keyArray[i]);
            }
        }
    }

    public Object getResultFromFile() throws Exception {
        File f = new File(FILE_PATH);
        ObjectInputStream fs = new ObjectInputStream(new FileInputStream(f));
        Object obj = fs.readObject();
        fs.close();
        return obj;
    }

    public void writeResult(Object obj) throws Exception {
        File f = new File(FILE_PATH);
        ObjectOutputStream fs = new ObjectOutputStream(new FileOutputStream(f));
        fs.writeObject(obj);
        fs.close();
    }

    public MD5Tools() {
        keyList = new ArrayList<String>();
    }

    public String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().substring(8, 24);// 16位的加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> createList() {
        List<String> list = new ArrayList<String>();
        for (String word : DICT) {
            list.add(word);
            strLength += word.length();
        }
        return list;
    }

    /**
     * printAll是输出全排列的递归调用方法，list是传入的list，用LinkedList实现， 而prefix用于转载以及输出的数据
     * length用于记载初始list的长度，用于判断程序结束。
     */
    public void createAllWords(List<String> candidate, String prefix, int length) {
        if (prefix.length() == length)
            keyList.add(prefix);
        for (int i = 0; i < candidate.size(); i++) {
            List<String> temp = new LinkedList<String>(candidate);
            createAllWords(temp, prefix + temp.remove(i), length);
        }
    }

    public void createWords(int removePos, ArrayList<String> candidate) {
        if (removePos == candidate.size() - 1) {

        }
        List<String> tempList = (List<String>) candidate.clone();
        tempList.remove(removePos);
        for (int i = 0; i < tempList.size(); i++) {
            createWords(removePos + 1, candidate);
        }
    }

    public LinkedList<String> getAllWords(String prefix, LinkedList<String> words) {

        if (words.size() == 1) {
            LinkedList<String> tempResult = new LinkedList<String>();
            keyList.add(prefix + words.get(0));
            keyList.add(words.get(0) + prefix);
            tempResult.add(prefix);
            tempResult.add(words.get(0));
            tempResult.add(prefix + words.get(0));
            tempResult.add(words.get(0) + prefix);
            return tempResult;
        }
        LinkedList<String> resultList = new LinkedList<String>();
        for (int i = 0; i < words.size() - 1; i++) {
            LinkedList<String> tempList = (LinkedList<String>) words.clone();
            LinkedList<String> subList = getAllWords(tempList.remove(i), tempList);
            for (String result : resultList) {
                keyList.add(prefix + result);
                keyList.add(result + prefix);
                keyList.add(prefix);
            }
            resultList.addAll(subList);
        }

        return resultList;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public int getStrLength() {
        return strLength;
    }

    public void setStrLength(int strLength) {
        this.strLength = strLength;
    }
}
