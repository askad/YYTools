package yy.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTools {

   private final static String URL_TEXT = "[\"'\\s;=]margin[^a-z]|[\"'\\s;=]padding[^a-z]";
    private final static String SFL_NAME = "S[\\w]+?Form.jsp";
    final static Pattern SFL_P = Pattern.compile(SFL_NAME, Pattern.CASE_INSENSITIVE);

    final static Pattern URL_P = Pattern.compile(URL_TEXT, Pattern.CASE_INSENSITIVE);

    public int count=0;
    public List<String> tempL;

    public boolean parser(String pageContent) throws Exception {
        // return
        // pageContent.contains("doFocus(document.getElementById('()')); changeContinueImage(this,'PFKEY04');");
//        Matcher matherCP = URL_P.matcher(pageContent);
//        if (matherCP.find()) {
//            String id = matherCP.group(1);
//            Pattern tempP = Pattern.compile("id[\\s]*=[\\s'\"]*\\s*" + id, Pattern.CASE_INSENSITIVE);
//            matherCP = tempP.matcher(pageContent);
//            if (matherCP.find()) {
//                return true;
//            } else {
//                return false;
//            }
//        }
        List<String> localTempL = new LinkedList(tempL);
        for(String namev:tempL){
        	if(pageContent.contains(namev)){
        		localTempL.remove(namev);
        	}
        }
        tempL = localTempL;
        return false;
        // return true;
    }

    public String readFile(File f) throws Exception {
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

    /**
     * match by file content
     * @param fd
     * @throws Exception
     */
    public void scanFile(File fd) throws Exception {
        if (fd.isDirectory()) {
            for (File f : fd.listFiles()) {
                scanFile(f);
            }
        } else {
        	if(fd.getName().endsWith(".java")||fd.getName().endsWith(".jsp")){
            count++;
          //  if (fd.getAbsolutePath().contains("\\eng\\")) {
                String content = this.readFile(fd);
                this.parser(content);
                if(tempL.isEmpty()){
                	System.out.println("all clear!");
                	System.exit(0);
                }
//                if (this.parser(content)) {
//                    System.out.println(fd.getName());
//                }
          //  }
        }
        }
    }

    /**
     * match by file name
     * @param fd
     * @throws Exception
     */
    public void checkFile(File fd) throws Exception {
        if (fd.isDirectory()) {
            for (File f : fd.listFiles()) {
                checkFile(f);
            }
        } else {
            count++;
            String fName = fd.getName();
            Matcher matherCP = SFL_P.matcher(fName);
            if (matherCP.find()) {
                System.out.println(fName);
            }
        }
    }

    public static void main(String[] s) throws Exception {

    	String[] names = {"COBOLHTMLFormatter.$AS5ZEROVS2","COBOLHTMLFormatter.$AS5ZEROVS2MINUS","COBOLHTMLFormatter.$S6ZEROVS2","COBOLHTMLFormatter.$S6ZEROVS2MINUS","COBOLHTMLFormatter.AAS5ZEROVS2","COBOLHTMLFormatter.AAS5ZEROVS2MINUS","COBOLHTMLFormatter.COMMA_DECIMAL_CREDIT","COBOLHTMLFormatter.COMMA_DECIMAL_CREDIT_ZEROSUPPRESS","COBOLHTMLFormatter.COMMA_DECIMAL_MINUSAFTER","COBOLHTMLFormatter.COMMA_DECIMAL_MINUSAFTER_ZEROSUPPRESS","COBOLHTMLFormatter.COMMA_DECIMAL_MINUSBEFORE_ASTER","COBOLHTMLFormatter.COMMA_DECIMAL_NOSIGN","COBOLHTMLFormatter.COMMA_DECIMAL_NOSIGN_ZEROSUPPRESS","COBOLHTMLFormatter.DATE_IGNOREDECIMAL","COBOLHTMLFormatter.DECIMAL_CREDIT","COBOLHTMLFormatter.DECIMAL_CREDIT_COMMAS_ZEROSUPPRESS","COBOLHTMLFormatter.DECIMAL_CREDIT_ZEROSUPPRESS","COBOLHTMLFormatter.DECIMAL_MINUSAFTER","COBOLHTMLFormatter.DECIMAL_MINUSAFTER_ZEROSUPPRESS","COBOLHTMLFormatter.DECIMAL_MINUSBEFORE","COBOLHTMLFormatter.DECIMAL_MINUSBEFORE_ZEROSUPPRESS","COBOLHTMLFormatter.DECIMAL_NOSIGN","COBOLHTMLFormatter.DECIMAL_NOSIGN_ZEROSUPPRESS","COBOLHTMLFormatter.IGNOREDECIMAL_BLANKSIGN","COBOLHTMLFormatter.S1","COBOLHTMLFormatter.S10$ZERO","COBOLHTMLFormatter.S10VS2","COBOLHTMLFormatter.S10VS2MINUS","COBOLHTMLFormatter.S10ZEROMINUS","COBOLHTMLFormatter.S11","COBOLHTMLFormatter.S11VS2","COBOLHTMLFormatter.S11VS4","COBOLHTMLFormatter.S12MS3","COBOLHTMLFormatter.S12CS3","COBOLHTMLFormatter.S12VS2","COBOLHTMLFormatter.S12ZEROMINUS","COBOLHTMLFormatter.S12ZEROS2VS2","COBOLHTMLFormatter.S12ZEROVS2","COBOLHTMLFormatter.S13VS2","COBOLHTMLFormatter.S13VS2MINUS","COBOLHTMLFormatter.S13ZEROS1VS2","COBOLHTMLFormatter.S13ZEROS1VS2CR","COBOLHTMLFormatter.S13ZEROS1VS2MINUS","COBOLHTMLFormatter.S14ZEROS1VS2CR","COBOLHTMLFormatter.S14ZEROS1VS2MINUS","COBOLHTMLFormatter.S15VS2","COBOLHTMLFormatter.S15VS2MINUS","COBOLHTMLFormatter.S15ZEROVS2","COBOLHTMLFormatter.S16VS2","COBOLHTMLFormatter.S1CS3CS3CS3CS3VS2","COBOLHTMLFormatter.S1MS3MS3MS3MS3VS2","COBOLHTMLFormatter.S1MS3MS3MS3MS3MS3VS2","COBOLHTMLFormatter.S1VS2","COBOLHTMLFormatter.S1VS3","COBOLHTMLFormatter.S1VS5","COBOLHTMLFormatter.S1ZERO","COBOLHTMLFormatter.S1ZEROS1VS2","COBOLHTMLFormatter.S1ZEROVS1","COBOLHTMLFormatter.S1ZEROVS2","COBOLHTMLFormatter.S1ZEROVS3","COBOLHTMLFormatter.S1ZEROVS4","COBOLHTMLFormatter.S1ZEROVS6","COBOLHTMLFormatter.S2","COBOLHTMLFormatter.S2CS3CS3CS3","COBOLHTMLFormatter.S2CS3CS3CS3CS3CS3","COBOLHTMLFormatter.S2CS3CS3CS3VS2","COBOLHTMLFormatter.S2MS3MS1ZEROS1VS2MINUS","COBOLHTMLFormatter.S2MS3MS3MS2$ZERO","COBOLHTMLFormatter.S2MS3MS3MS2ZERO","COBOLHTMLFormatter.S2MS3MS3MS3","COBOLHTMLFormatter.S2MS3MS3MS3MS3MS3","COBOLHTMLFormatter.S2MS3MS3MS3VS2","COBOLHTMLFormatter.S2PCT","COBOLHTMLFormatter.S2V","COBOLHTMLFormatter.S2VS1","COBOLHTMLFormatter.S2VS1ZERO","COBOLHTMLFormatter.S2VS2","COBOLHTMLFormatter.S2VS2PCT","COBOLHTMLFormatter.S2VS2ZERO","COBOLHTMLFormatter.S2VS3","COBOLHTMLFormatter.S2VS4","COBOLHTMLFormatter.S2VS6","COBOLHTMLFormatter.S2ZEROPCT","COBOLHTMLFormatter.S2ZEROVS2","COBOLHTMLFormatter.S2ZEROVS3","COBOLHTMLFormatter.S2ZEROVS8","COBOLHTMLFormatter.S3","COBOLHTMLFormatter.S3$ZERO","COBOLHTMLFormatter.S3CS3","COBOLHTMLFormatter.S3CS3CS3CS3CS3CS3","COBOLHTMLFormatter.S3CS3CS3CS3CS3VS3","COBOLHTMLFormatter.S3CS3CS3MINUS","COBOLHTMLFormatter.S3MS3","COBOLHTMLFormatter.S3MS3MS1$ZEROS1VS2MINUS","COBOLHTMLFormatter.S3MS3MS1ZEROS1VS2MINUS","COBOLHTMLFormatter.S3MS3MS2$ZEROVS2MINUS","COBOLHTMLFormatter.S3MS3MS2ZERO","COBOLHTMLFormatter.S3MS3S1$ZEROS1VS2MINUS","COBOLHTMLFormatter.S3MS3MS3MINUS","COBOLHTMLFormatter.S3MS3MS3MS3MS3MS3","COBOLHTMLFormatter.S3MS3MS3MS3MS3VS2","COBOLHTMLFormatter.S3MS3S2ZERO","COBOLHTMLFormatter.S3PCT","COBOLHTMLFormatter.S3VS1","COBOLHTMLFormatter.S3VS2","COBOLHTMLFormatter.S3VS2PCT","COBOLHTMLFormatter.S3VS3","COBOLHTMLFormatter.S3VS4","COBOLHTMLFormatter.S3VS5","COBOLHTMLFormatter.S3VS6","COBOLHTMLFormatter.S3VS7","COBOLHTMLFormatter.S3VS8","COBOLHTMLFormatter.S3VS9","COBOLHTMLFormatter.S3ZERO","COBOLHTMLFormatter.S4","COBOLHTMLFormatter.S4$ZERO","COBOLHTMLFormatter.S4$ZEROVS2","COBOLHTMLFormatter.S4MINUS","COBOLHTMLFormatter.S4V","COBOLHTMLFormatter.S4VS1","COBOLHTMLFormatter.S4VS2","COBOLHTMLFormatter.S4VS2MINUS","COBOLHTMLFormatter.S4VS3","COBOLHTMLFormatter.S4VS4","COBOLHTMLFormatter.S4VS5","COBOLHTMLFormatter.S4ZERO","COBOLHTMLFormatter.S4ZEROVS2MINUS","COBOLHTMLFormatter.S5","COBOLHTMLFormatter.S5VS1","COBOLHTMLFormatter.S5VS2","COBOLHTMLFormatter.S5VS3","COBOLHTMLFormatter.S5VS4","COBOLHTMLFormatter.S5VS7","COBOLHTMLFormatter.S5ZERO","COBOLHTMLFormatter.S5ZEROV2MINUS","COBOLHTMLFormatter.S5ZEROVS2","COBOLHTMLFormatter.S5ZEROVS2MINUS","COBOLHTMLFormatter.S6$ZERO","COBOLHTMLFormatter.S6$ZEROVS2","COBOLHTMLFormatter.S6$ZEROVS2MINUS","COBOLHTMLFormatter.S6VS2","COBOLHTMLFormatter.S6VS4","COBOLHTMLFormatter.S6ZEROS1MINUS","COBOLHTMLFormatter.S6ZEROVS2MINUS","COBOLHTMLFormatter.S7","COBOLHTMLFormatter.S7VS2","COBOLHTMLFormatter.S7VS2MINUS","COBOLHTMLFormatter.S7VS6","COBOLHTMLFormatter.S7ZEROVS2MINUS","COBOLHTMLFormatter.S8$ZERO","COBOLHTMLFormatter.S8$ZEROVS2","COBOLHTMLFormatter.S8VS2","COBOLHTMLFormatter.S8ZERO","COBOLHTMLFormatter.S8ZEROMINUS","COBOLHTMLFormatter.S8ZEROS1VS2","COBOLHTMLFormatter.S8ZEROVS2MINUS","COBOLHTMLFormatter.S8ZEROVS9","COBOLHTMLFormatter.S9$ZERO","COBOLHTMLFormatter.S9VS2","COBOLHTMLFormatter.S9VS9","COBOLHTMLFormatter.S9ZEROS1VS2CR","COBOLHTMLFormatter.S9ZEROS1VS2MINUS","COBOLHTMLFormatter.S9ZEROVS2","COBOLHTMLFormatter.VS2PCT","COBOLHTMLFormatter.Z10","COBOLHTMLFormatter.Z3","COBOLHTMLFormatter.Z4","COBOLHTMLFormatter.ZEROS1","COBOLHTMLFormatter.ZEROS1VS2","COBOLHTMLFormatter.ZEROS1VS2APCT","COBOLHTMLFormatter.ZEROS1VS2VS2","COBOLHTMLFormatter.ZEROS1VS3"};
        FileTools ft = new FileTools();
        ft.tempL = new LinkedList<String>();
    	for(String namev:names){
    		ft.tempL.add(namev);
    	}
        //File fd = new File("src/main/webapp");
        File fd = new File("/Project/Life4I18N/");

        ft.scanFile(fd);
        for(String noused:ft.tempL){
        	System.out.println(noused);
        }
    }
}
