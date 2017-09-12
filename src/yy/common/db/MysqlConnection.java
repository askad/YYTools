package yy.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public abstract class MysqlConnection {
    private String url;
    private String user;
    private String password;
    private Connection conn;
    private PreparedStatement statementBatch;

    protected List<Object> getSelectData(ResultSet rs) throws Exception {
        return null;
    };

    public MysqlConnection(String url, String user, String password) throws Exception {
        super();
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName("com.mysql.jdbc.Driver");
        initConnection();
    }

    public Connection initConnection() throws Exception {
        conn = (Connection) DriverManager.getConnection(url, user, password);
        if (!conn.isClosed())
            System.out.println("Succeeded connecting to the Database!");
        return conn;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertData(Object obj) throws Exception {
        PreparedStatement statement = (PreparedStatement) conn.prepareStatement(getSql());
        setValue(statement, obj);
        int i = statement.executeUpdate();
        return i;
    }
    
    public void insertBatchData(Object obj) throws Exception {
        if(statementBatch == null){
            statementBatch = (PreparedStatement) conn.prepareStatement(getSql());
        }
        setValue(statementBatch, obj);
        statementBatch.addBatch();
    }
    
    public void closeBatch() throws Exception {
        statementBatch.executeBatch();
        closeConn();
    }

    public List<Object> selectData(Object item) throws Exception {
        PreparedStatement statement = (PreparedStatement) conn.prepareStatement(getSql());
        setValue(statement, item);
        ResultSet rs = statement.executeQuery();
        List<Object> obj = getSelectData(rs);
        return obj;
    }
    
    

    abstract protected void setValue(PreparedStatement statement, Object obj) throws SQLException;

    abstract protected String getSql();
}
