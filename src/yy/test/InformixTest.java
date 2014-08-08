package yy.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import yy.common.ResourceBundleUtil;

public class InformixTest {
    public static void main(String[] args) throws Exception {
        Connection conn;
        String url = ResourceBundleUtil.getInstance().getString("urlimx");
        try {
            // Load the Informix JDBC Driver
            // DriverManager.registerDriver((Driver)
            // Class.forName("com.informix.jdbc.IfxDriver").newInstance());

            BufferedWriter bw = new BufferedWriter(new FileWriter("d:/sysuser.txt"));

            Class.forName("com.informix.jdbc.IfxDriver");

            // Create and open a server/database connection
            conn = (Connection) DriverManager.getConnection(url);

            // Queries that return more than one row
            Statement query = null;
            ResultSet rs = null;
            try {
                query = conn.createStatement();
                rs = query.executeQuery("select * from account ");
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    System.out.print(rs.getMetaData().getColumnName(i)+"\t");
                }
                while (rs.next()) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        String str = rs.getString(i);
                        if(str==null){
                            str=" ";
                        }
                        bw.write(str);
                        bw.write("\\t");
                       System.out.println(i);
                    }
                    bw.write("\r\n");
                }
                bw.close();
                rs.close();
                query.close();
            } catch (SQLException exce) {
                System.out.println(exce);
                System.out.println("Caught: " + exce.getErrorCode());
            }
            conn.close();
        } catch (ClassNotFoundException drvEx) {
            System.err.println("Could not load JDBC driver");
            System.out.println("Exception: " + drvEx);
            drvEx.printStackTrace();
        } catch (SQLException sqlEx) {
            while (sqlEx != null) {
                System.err.println("SQLException information");
                System.err.println("Error msg: " + sqlEx.getMessage());
                System.err.println("SQLSTATE: " + sqlEx.getSQLState());
                System.err.println("Error code: " + sqlEx.getErrorCode());
                sqlEx.printStackTrace();
                sqlEx = sqlEx.getNextException();
            }
        }
    }
}
