package yy.datascan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import yy.third.CsvReader;

public class DataTransformer2000W {

    final static String IT_SQL = "INSERT INTO `usr_hoteljj` (`Name`, `CardNo`, `Descriot`, `CtfTp`, `CtfId`, `Gender`, `Birthday`, `Address`, `Zip`, `Dirty`, `District1`, `District2`, `District3`, `District4`, `District5`, `District6`, `FirstNm`, `LastNm`, `Duty`, `Mobile`, `Tel`, `Fax`, `EMail`, `Nation`, `Taste`, `Education`, `Company`, `CTel`, `CAddress`, `CZip`, `Family`, `Version`, `id`) VALUES (";

    // '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
    // '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
    // '1', '1', '1', '1', '1')";
    public void readCsv() {
    }

    public static void main(String[] args) {
        DataTransformer2000W dt = new DataTransformer2000W();
        String fdstr = "";
        File fd = new File(fdstr);
        for(String fName:fd.list()){
            if(fName.endsWith(".csv")){
                dt.createSqlFile(fdstr+fName);
            }
        }
    }

    public void createSqlFile(String csvName){


        String fileName = csvName;//"D:/YYANG21/Personal/Information/2000W(csv格式)/1-200W.csv";
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
            CsvReader hotelInfo = new CsvReader(fileName, ',', Charset.forName("utf-8"));
            hotelInfo.readHeaders();
            while (hotelInfo.readRecord()) {

                if (checkfillterString(hotelInfo.getValues())) {
                    StringBuilder sb = new StringBuilder(IT_SQL);
                    String name = fillterString(hotelInfo.get(hotelInfo.getHeader(0)));
                    String ctfId = fillterString(hotelInfo.get("CtfId"));                    
                    if ((name == null || name.isEmpty()) && (ctfId == null || ctfId.isEmpty() || "CHN".equals(ctfId))) {
                        continue;
                    }
                    
                    String cardNo = fillterString(hotelInfo.get("CardNo"));
                    String descriot = fillterString(hotelInfo.get("Descriot"));
                    String ctfTp = fillterString(hotelInfo.get("CtfTp"));
                    String gender = fillterString(hotelInfo.get("Gender"));
                    String birthday = fillterString(hotelInfo.get("Birthday"));
                    String address = fillterString(hotelInfo.get("Address"));
                    String zip = fillterString(hotelInfo.get("Zip"));
                    String dirty = fillterString(hotelInfo.get("Dirty"));
                    String district1 = fillterString(hotelInfo.get("District1"));
                    String district2 = fillterString(hotelInfo.get("District2"));
                    String district3 = fillterString(hotelInfo.get("District3"));
                    String district4 = fillterString(hotelInfo.get("District4"));
                    String district5 = fillterString(hotelInfo.get("District5"));
                    String district6 = fillterString(hotelInfo.get("District6"));
                    String firstNm = fillterString(hotelInfo.get("FirstNm"));
                    String lastNm = fillterString(hotelInfo.get("LastNm"));
                    String duty = fillterString(hotelInfo.get("Duty"));
                    String mobile = fillterString(hotelInfo.get("Mobile"));
                    String tel = fillterString(hotelInfo.get("Tel"));
                    String fax = fillterString(hotelInfo.get("Fax"));
                    String eMail = fillterString(hotelInfo.get("EMail"));
                    String nation = fillterString(hotelInfo.get("Nation"));
                    String taste = fillterString(hotelInfo.get("Taste"));
                    String education = fillterString(hotelInfo.get("Education"));
                    String company = fillterString(hotelInfo.get("Company"));
                    String cTel = fillterString(hotelInfo.get("CTel"));
                    String cAddress = fillterString(hotelInfo.get("CAddress"));
                    String cZip = fillterString(hotelInfo.get("CZip"));
                    String family = fillterString(hotelInfo.get("Family"));
                    String version = fillterString(hotelInfo.get("Version"));
                    String id = fillterString(hotelInfo.get("Id"));
                    // sb.append(name+"\r\n");
                    sb.append("'" + name + "',");
                    sb.append("'" + cardNo + "',");
                    sb.append("'" + descriot + "',");
                    sb.append("'" + ctfTp + "',");
                    sb.append("'" + ctfId + "',");
                    sb.append("'" + gender + "',");
                    sb.append("'" + birthday + "',");
                    sb.append("'" + address + "',");
                    sb.append("'" + zip + "',");
                    sb.append("'" + dirty + "',");
                    sb.append("'" + district1 + "',");
                    sb.append("'" + district2 + "',");
                    sb.append("'" + district3 + "',");
                    sb.append("'" + district4 + "',");
                    sb.append("'" + district5 + "',");
                    sb.append("'" + district6 + "',");
                    sb.append("'" + firstNm + "',");
                    sb.append("'" + lastNm + "',");
                    sb.append("'" + duty + "',");
                    sb.append("'" + mobile + "',");
                    sb.append("'" + tel + "',");
                    sb.append("'" + fax + "',");
                    sb.append("'" + eMail + "',");
                    sb.append("'" + nation + "',");
                    sb.append("'" + taste + "',");
                    sb.append("'" + education + "',");
                    sb.append("'" + company + "',");
                    sb.append("'" + cTel + "',");
                    sb.append("'" + cAddress + "',");
                    sb.append("'" + cZip + "',");
                    sb.append("'" + family + "',");
                    sb.append("'" + version + "',");
                    sb.append("'" + id + "');\r\n");
                    fw2.write(sb.toString());
                } else {
                    StringBuilder sb = new StringBuilder(IT_SQL);

                    String name = hotelInfo.get(hotelInfo.getHeader(0));
                    String cardNo = hotelInfo.get("CardNo");
                    String descriot = hotelInfo.get("Descriot");
                    String ctfTp = hotelInfo.get("CtfTp");
                    String ctfId = hotelInfo.get("CtfId");
                    String gender = hotelInfo.get("Gender");
                    String birthday = hotelInfo.get("Birthday");
                    String address = hotelInfo.get("Address");
                    String zip = hotelInfo.get("Zip");
                    String dirty = hotelInfo.get("Dirty");
                    String district1 = hotelInfo.get("District1");
                    String district2 = hotelInfo.get("District2");
                    String district3 = hotelInfo.get("District3");
                    String district4 = hotelInfo.get("District4");
                    String district5 = hotelInfo.get("District5");
                    String district6 = hotelInfo.get("District6");
                    String firstNm = hotelInfo.get("FirstNm");
                    String lastNm = hotelInfo.get("LastNm");
                    String duty = hotelInfo.get("Duty");
                    String mobile = hotelInfo.get("Mobile");
                    String tel = hotelInfo.get("Tel");
                    String fax = hotelInfo.get("Fax");
                    String eMail = hotelInfo.get("EMail");
                    String nation = hotelInfo.get("Nation");
                    String taste = hotelInfo.get("Taste");
                    String education = hotelInfo.get("Education");
                    String company = hotelInfo.get("Company");
                    String cTel = hotelInfo.get("CTel");
                    if ((name == null || name.isEmpty()) && (ctfId == null || ctfId.isEmpty() || "CHN".equals(ctfId))) {
                        continue;
                    }
                    String cAddress = hotelInfo.get("CAddress");
                    String cZip = hotelInfo.get("CZip");
                    String family = hotelInfo.get("Family");
                    String version = hotelInfo.get("Version");
                    String id = hotelInfo.get("Id");

                    // sb.append(name+"\r\n");
                    sb.append("'" + name + "',");
                    sb.append("'" + cardNo + "',");
                    sb.append("'" + descriot + "',");
                    sb.append("'" + ctfTp + "',");
                    sb.append("'" + ctfId + "',");
                    sb.append("'" + gender + "',");
                    sb.append("'" + birthday + "',");
                    sb.append("'" + address + "',");
                    sb.append("'" + zip + "',");
                    sb.append("'" + dirty + "',");
                    sb.append("'" + district1 + "',");
                    sb.append("'" + district2 + "',");
                    sb.append("'" + district3 + "',");
                    sb.append("'" + district4 + "',");
                    sb.append("'" + district5 + "',");
                    sb.append("'" + district6 + "',");
                    sb.append("'" + firstNm + "',");
                    sb.append("'" + lastNm + "',");
                    sb.append("'" + duty + "',");
                    sb.append("'" + mobile + "',");
                    sb.append("'" + tel + "',");
                    sb.append("'" + fax + "',");
                    sb.append("'" + eMail + "',");
                    sb.append("'" + nation + "',");
                    sb.append("'" + taste + "',");
                    sb.append("'" + education + "',");
                    sb.append("'" + company + "',");
                    sb.append("'" + cTel + "',");
                    sb.append("'" + cAddress + "',");
                    sb.append("'" + cZip + "',");
                    sb.append("'" + family + "',");
                    sb.append("'" + version + "',");
                    sb.append("'" + id + "');\r\n");
                    fw.write(sb.toString());
                }
            }
            fw.close();
            fw2.close();
            hotelInfo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    
    }
    public String fillterString(String text) {
        if (text != null && !text.isEmpty()) {
            text = text.replaceAll("\\\\", "\\\\\\\\");
            text = text.replaceAll("'", "\\\\'");
            text = text.replaceAll("\"", "\\\\\"");
        }
        return text;
    }

    public boolean checkfillterString(String[] values) {
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
        }
        String text = sb.toString();
        if (!text.isEmpty()) {
            if (text.contains("'") || text.contains("\"")|| text.contains("\\")) {
                return true;
            }
        }
        return false;
    }
}
