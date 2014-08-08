package yy.datascan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class DataTransformerTianYa {

    final static String IT_SQL = "INSERT  INTO `usr_tianya` (`usr_name`, `usr_pass`, `usr_mail`) VALUES ('";
    final static String MATCHER = "[A-Za-z0-9_-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]*";
    static Pattern P = Pattern.compile(MATCHER);

    public static void main(String[] args) {
        
        DataTransformerTianYa dt = new DataTransformerTianYa();
        String fdstr = "tianya_1.txt";
        dt.createSqlFile(fdstr);
//        File fd = new File(fdstr);
//        for (String fName : fd.list()) {
//            if (fName.endsWit(".csv")) {
//                dt.createSqlFile(fdstr + fName);
//            }
//        }
    }

    public void createSqlFile(String csvName) {

        String fileName = csvName;
        File file = new File(fileName);
        String newFileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
        String newFileFullName = file.getParent() + "\\" + newFileName + ".sql";
        String newFileFullName2 = file.getParent() + "\\" + newFileName + "2.sql";
        File fileNew = new File(newFileFullName);
        File fileNew2 = new File(newFileFullName2);
        try {
            fileNew.createNewFile();
            fileNew2.createNewFile();
            BufferedWriter fw = new BufferedWriter(new FileWriter(newFileFullName));
            BufferedWriter fw2 = new BufferedWriter(new FileWriter(newFileFullName2));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "gbk"));
            String content = null;
            while ((content = br.readLine()) != null) {

                String[] result = content.split("\\t+");
                if (result.length > 3) {
                    content.replaceAll("\t", "");
                    result = content.split("\\t+");
                }
                if (result.length > 3) {
                    fw2.write("33: " + content + "\r\n");
                    continue;
                }
                if (result.length <= 2) {
                    fw2.write("22: " + content + "\r\n");
                    continue;
                }
                if(!result[2].contains("@")){
                    fw2.write("22: " + content + "\r\n");
                    continue;
                }
                if(result[2].length()<5){
                    fw2.write("45: " + content + "\r\n");
                    continue;
                }
                if (!P.matcher(result[2]).matches()) {
                    fw2.write("@@:" + content + "\r\n");
                    continue;
                }
                StringBuilder sb = new StringBuilder(IT_SQL);
                sb.append(result[0]);
                sb.append("','");
                sb.append(result[1]);
                sb.append("','");
                sb.append(result[2]);
                sb.append("');");
                fw.write(sb.toString()+"\r\n");
            }
            br.close();
            fw.close();
            fw2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
