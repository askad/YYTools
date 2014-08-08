package yy.datascan;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 1.分组为用户，密码，邮箱 2.密码部分低于4位加入
 * 
 * @author yyang21
 * 
 */
public class DataFillter {

    /**
     * 1.check if can use=>save to table 2.check password a.length<6 pass
     * 
     * @throws FileNotFoundException
     * 
     */
    public void fillter126() throws Exception {
        boolean flag = true;
        int count = 0;
        int fcount = 0;
        int ecount = 0;
        // TODO
        BufferedReader brf = new BufferedReader(new FileReader("/data/126.com/3.txt"));

        String content;
        StringBuffer sb = new StringBuffer();
        StringBuffer sber = new StringBuffer();
        // StringBuffer sb126 = new StringBuffer();
        while ((content = brf.readLine()) != null) {
            // sb.append(insSql);
            String[] res = content.trim().split("\t");
            if (res == null || res.length != 2) {
                sber.append(content);
                sber.append("\r\n");
                ecount++;
                // flag = false;
                // System.out.print(content + ":" + count);
                // break;
                if (ecount == 10000) {
                    FileWriter fw = new FileWriter("/data/126.com/3err" + fcount
                            + ".txt");
                    fw.write(sber.toString());
                    fw.close();
                    fcount++;
                    sber = new StringBuffer();
                }
                continue;
            }
            //TODO
            if (res[1].contains("MYPASS")) {
                sber.append(content);
                sber.append("\r\n");
                ecount++;
                // flag = false;
                // System.out.print(content + ":" + count);
                // break;
                if (ecount == 10000) {
                    FileWriter fw = new FileWriter("/data/126.com/3err" + fcount
                            + ".txt");
                    fw.write(sber.toString());
                    fw.close();
                    fcount++;
                    sber = new StringBuffer();
                }
                continue;
            }
            sb.append(res[0]);
            sb.append(",");
            sb.append(res[1]);
            sb.append("\r\n");
            // sb.append("','126');/r/n");
            count++;
            if (count == 1000000) {
                FileWriter fw = new FileWriter("/data/126.com/3bak" + fcount + ".txt");
                fw.write(sb.toString());
                fw.close();
                count = 0;
                sb = new StringBuffer();
                fcount++;
            }
            //
            // //sb126.append(ins126Sql);
            // sb126.append(res[0]);
            // sb126.append("','");
            // sb126.append(res[1]);
            // sb126.append("');/r/n");
        }
        brf.close();

        if (count != 0) {
            FileWriter fw = new FileWriter("/data/126.com/3bak" + fcount + ".txt");
            fw.write(sb.toString());
            fw.close();
        }
        if (flag) {
            FileWriter fw126 = new FileWriter("/data/126.com/3errerr.txt");
            fw126.write(sber.toString());
            fw126.close();
        }
    }

    public static void main(String[] args) {
        DataFillter df = new DataFillter();
        try {
            df.fillter126();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
