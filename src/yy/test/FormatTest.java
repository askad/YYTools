package yy.test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;



public class FormatTest {

	 public  static void customFormat(String pattern, double value ) {
     	DecimalFormat myFormatter = new DecimalFormat(pattern);
     	String output = myFormatter.format(value);
     	System.out.println(value + " " + pattern + " " + output);
     	}
   public static void localizedFormat(String pattern, double value, Locale loc ) {
     	NumberFormat nf = NumberFormat.getNumberInstance(loc);
     	DecimalFormat df = (DecimalFormat)nf;
     	df.applyPattern(pattern);
     	String output = df.format(value);
     	System.out.println(pattern + " " + output + " " + loc.toString());
     	}

    public static void main(String[] args) {
//     	customFormat("###,###.###", 123456.789);
//     	customFormat("###.##", 123456.789);
//     	customFormat("000000.000", 123.78);
//     	customFormat("$###,###.###", 12345.67);
//     	customFormat("\u00a5###,###.###", 12345.67);
     	Locale currentLocale = new Locale("en", "US");
     	DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(currentLocale);
//     	unusualSymbols.setDecimalSeparator('|');
//     	unusualSymbols.setGroupingSeparator('^');
     	String strange = "#,###.###";
     	DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
     	weirdFormatter.setGroupingSize(4);
     	String bizarre = weirdFormatter.format(12345.678);
     	System.out.println(bizarre);
     	}


    class Task3 implements Runnable {

        public int x;

        @Override
        public void run() {
            // TODO Auto-generated method stub

            int j;
            while (true) {
                synchronized (this) {
                    j = x + 1;
                }
                this.test1(j);
                synchronized (this) {
                    j = x + 1;
                }
                this.test2(j);

            }

        }

        public void test1(int j) {
            synchronized (this) {
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                x = j;

                String threadName = Thread.currentThread().getName();

                System.out.println(threadName + " test1 " + x);
            }
        }

        public void test2(int j) {
            synchronized (this) {
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                x = j;

                String threadName = Thread.currentThread().getName();

                System.out.println(threadName + " test2 " + x);
            }
        }


    }

//
//
//    public static void main(String[] args) throws Exception {
//
//        StringBuffer sb = new StringBuffer();
//        try {
//            UnicodeReader ur = new UnicodeReader(new FileInputStream("C:/temp/ttest/AP28079612543722.xml"), "UTF-16BE");
//            BufferedReader br = new BufferedReader(ur);
//            String cont = null;
//            while ((cont = br.readLine()) != null) {
//                sb.append(cont);
//            }
//            ur.close();
//            br.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String result = sb.toString();
//
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true); // never forget this!
//        DocumentBuilder builder;
//        NodeList ownersel = null;
//        NodeList sumin = null;
//        try {
//            builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(new InputSource(new ByteArrayInputStream(result.getBytes())));
//            XPathFactory factoryXpath = XPathFactory.newInstance();
//            XPath xpath = factoryXpath.newXPath();
//            XPathExpression ownerExpr = xpath.compile("//iposDocument/@uid");
//            ownersel = (NodeList)ownerExpr.evaluate(doc, XPathConstants.NODESET);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < ownersel.getLength(); i++) {
//            System.out.println(ownersel.item(i).getNodeValue());
//        }
//
//
////       System.out.println(ownersel.toString());
////       System.out.println(sumin.toString());
//    }
////        String[] groupKeys = new String[] { 'policy_no' };
//        String[] groupKeys = new String[] { 'ORIGINAL_POLICY_NO' };
////      System.out.println(new TempTest().getTotalAmountToPaybyGroup(groupKeys, new String[] { 'policy_no' }, 'F006',
////      true));
//      System.out.println(new TempTest().getReceivableBizTrans(groupKeys, new String[] { '1068101022013000001-01' }, true,new String[] {'P001','P003'}));
//    }
//    public String getReceivableBizTrans(String[] groupKeys, String[] groupValues, boolean distinguishPlyEdr, String[] bizTranTypes) {
////
//
//
////        System.out.println(new TempTest().getReceivableBizTrans(groupKeys, new String[] { '1068101022013000001-01' }, true,new String[] {'P001','P003'}));
//
//        StringBuilder sql = new StringBuilder();
//        List<String> params = new ArrayList<String>();
//        sql.append('select balance, amount, instalment_no from biz_trans where ');//fix #792
//        sql.append(GenUtils.buildSqlPreparedStatement(groupKeys, distinguishPlyEdr));
//        for (int i = 0; i < groupValues.length; i++) {
//            if (!distinguishPlyEdr && i == 0) {
//                params.add(groupValues[i] + '%');
//            } else {
//                params.add(groupValues[i]);
//            }
//        }
//        sql.append(' and biz_tran_type in ('+GenUtils.splitSqlString2(bizTranTypes)+') ');
//        //params.add(GenUtils.splitSqlString2(bizTranTypes));
//        sql.append(' and reversal_flag = 'N' ');
//        //å…±ä¿� å‡ºå�•è´¹
//        if(groupKeys[0].equalsIgnoreCase('policy_no') && distinguishPlyEdr){
//            if ((groupKeys.length == 1 && Arrays.equals(bizTranTypes, new String[] {'P001', 'P003' }))
//                    || (groupKeys.length == 2 && Arrays.equals(bizTranTypes, new String[] { 'P009' }))) {
//                sql.append(' and confirmation_status = 'Y' ');
//            }
//        }
//
//        return sql.toString();
//
//    }
//
//
//    public String getTotalAmountToPaybyGroup(String[] groupKeys, String[] bizTranNos, String bizTranTypes,
//            boolean distinguishPlyEdr) {
//
//        StringBuilder stmt = new StringBuilder();
//        List<String> params = new ArrayList<String>();
//        // å�–å¾—policy_no
//        String policyNo = groupKeys[0];
//        stmt.append('select ');
//        String[] columns = new String[] {};
//        if (distinguishPlyEdr) {
//            stmt.append(GenUtils.splitSqlString(groupKeys)).append(', ');
//        } else {
//            String policyNoSubstr = 'substr(' + policyNo + ', 0, 19) as policyNoSubstr, ';
//            stmt.append(policyNoSubstr);
//            columns = (String[]) ArrayUtils.removeElement(groupKeys, policyNo);
//            if (columns.length > 0) {
//                stmt.append(GenUtils.splitSqlString(columns)).append(', ');
//            }
//        }
//        if (bizTranNos.length == 1) {
//            stmt.append('sum(amount) as amt from biz_trans where ( biz_tran_no = ? ');
//            params.add(GenUtils.splitSqlString(bizTranNos));
//        } else {
//            int per = 900, k = 0;
//            int f = bizTranNos.length % per;
//            int start = 0, to = per;
//            String[] s = null;
//            stmt.append('sum(amount) as amt from biz_trans where ( ');
//            for (String str : bizTranNos) {
//                ++k;
//                if (k == per) {
//                    s = (String[]) ArrayUtils.subarray(bizTranNos, start, to);
//                    start += per;
//                    to += per;
//                    stmt.append(' or biz_tran_no in (' + GenUtils.splitSqlString2(s) + ') ');
//                    k = 0;
//                }
//            }
//            if (stmt.indexOf(' or ') != -1) {
//                stmt.delete(stmt.indexOf(' or '), stmt.indexOf(' or ') + 4);
//            }
//            s = (String[]) ArrayUtils.subarray(bizTranNos, bizTranNos.length - f, bizTranNos.length);
//            if (bizTranNos.length > per && f > 0) {
//                stmt.append(' or biz_tran_no in (' + GenUtils.splitSqlString2(s) + ') ');
//            } else if (f != 0) {
//                stmt.append(' biz_tran_no in (' + GenUtils.splitSqlString2(s) + ') ');
//            }
//        }
//        String[] splitStr = bizTranTypes.split(',');
//        if (splitStr.length < 2) {
//            stmt.append(') and biz_tran_type = ? ');
//            params.add(bizTranTypes);
//            if (bizTranTypes.equals('F006')) {
//                if (groupKeys.length == 1) {// å…±ä¿�å…±å…¥éƒ¨åˆ†çš„å‡ºå�•è´¹
//                // stmt.append(' and coinsurer_number = '119001' ');
//                    stmt.append(' and SOURCE_TYPE = 'COIN' '); // 'COIN' å…±ä¿�åˆ†å…¥
//                } else {// å…±ä¿�å‰¯å�•éƒ¨åˆ†çš„å‡ºå�•è´¹
//                // stmt.append(' and coinsurer_number <> '119001' ');
//                    stmt.append(' and SOURCE_TYPE = 'COUO' '); // 'COUO' å…±ä¿�åˆ†å‡º
//                }
//            }
//        } else {
//            stmt.append(') and biz_tran_type in (' + GenUtils.splitSqlString2(splitStr) + ') ');
//        }
//        stmt.append('group by ');
//        if (distinguishPlyEdr) {
//            stmt.append(GenUtils.splitSqlString(groupKeys));
//        } else {
//            stmt.append('substr(' + policyNo + ',0,19) ');
//            if (columns.length > 0) {
//                stmt.append(', ' + GenUtils.splitSqlString(columns));
//            }
//        }
//
//        return stmt.toString();
//    }
//    // List<String> collectionAllocTableVoListTemp = new ArrayList<String>();
//    // for (int j = 3000; j < 5000; j++) {
//    // for (int i = 0; i < j; i++) {
//    // collectionAllocTableVoListTemp.add(new Integer(i).toString());
//    // }
//    // boolean x= new TempTest().testMethod(collectionAllocTableVoListTemp, 50);
//    // if(!x){
//    // System.out.println(j);
//    // }
//    // collectionAllocTableVoListTemp.clear();
//    // }
//    public boolean testMethod(List<String> collectionAllocTableVoListTemp, int mcount) {
//
//        int upSize = collectionAllocTableVoListTemp.size();
//        List<List<Integer>> bizs = new ArrayList<List<Integer>>();
//        // Get Threshold
//        int maxCount = 10;
//        Integer count = mcount;
//        if (count != null && count > 0) {
//            maxCount = count;
//        }
//        // å°†æ‰€æœ‰ä½œä¸šåˆ†ç‰‡å¤„ç�†ï¼Œå¤šå�¯åŠ¨ä¸€ä¸ªçº¿ç¨‹ç”¨äºŽå¤„ç�†ä½™æ•°
//        int eachJobCount = (upSize / maxCount + 1);
//
//        // fix bug when maxCount > upSize
//        if (upSize <= maxCount) {
//            eachJobCount = 1;
//        }
//
//        for (int i = 0; i <= maxCount; i++) {
//            int start = i * eachJobCount;
//            int end = (i + 1) * eachJobCount;
//            if (start >= upSize) {
//                break;
//            }
//            if (end > upSize) {
//                end = upSize;
//            }
//            System.out.println('test:' + i);
//            System.out.println(start);
//            System.out.println(end);
//            List<String> collectionAllocTableVoList = collectionAllocTableVoListTemp.subList(start, end);
//            List<Integer> bizList = new ArrayList<Integer>();
//            bizs.add(bizList);
//
//            for (int j = 0; j < collectionAllocTableVoList.size(); j++) {
//                String collectionAllocTableVo = collectionAllocTableVoList.get(j);
//                bizList.add(new Integer(j));
//            }
//        }
//        List<Integer> bizAllList = new ArrayList<Integer>();
//        for (List<Integer> bizList : bizs) {
//            bizAllList.addAll(bizList);
//        }
//
//        if (bizAllList.size() != collectionAllocTableVoListTemp.size()) {
//            System.out.println('xxx:');
//            System.out.println(bizAllList.size());
//            System.out.println(collectionAllocTableVoListTemp.size());
//            return false;
//        } else {
//            // System.out.println('ok');
//        }
//        return true;
//    }
}
class A{
    public  String toString(){
        return "'xxx'";
    }
}