package yy.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class MysqlConnection {
	{
		// 加载驱动程序
		Class.forName("com.mysql.jdbc.Driver");
	}
	private String url;
	private String user;
	private String password;

	protected List<Object> getSelectData(ResultSet rs) throws Exception {
		return null;
	};

	public MysqlConnection() throws Exception {
		ResourceBundleUtil rt = ResourceBundleUtil.getInstance();
		// URL指向要访问的数据库名scutcs
		url = rt.getString("url");

		// MySQL配置时的用户名
		user = rt.getString("user");

		// Java连接MySQL配置时的密码
		password = rt.getString("password");
	}

	public Connection getConnection() throws Exception {

		// 连续数据库
		Connection conn = (Connection) DriverManager.getConnection(url, user,
				password);
		if (!conn.isClosed())
			System.out.println("Succeeded connecting to the Database!");

		return conn;
	}

	private void closeConn(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public int insertData(String sql, BaseVo baseVo) throws Exception {
	 * Connection conn = getConnection(); // statement用来执行SQL语句
	 * PreparedStatement statement = (PreparedStatement)
	 * conn.prepareStatement(sql); setInsertPS(statement, baseVo); // 要执行的SQL语句
	 * int i = statement.executeUpdate(); closeConn(conn); return i; }
	 * 
	 * public List<Object> selectDate(String sql, BaseVo baseVo) throws
	 * Exception { Connection conn = getConnection(); // statement用来执行SQL语句
	 * PreparedStatement statement = (PreparedStatement)
	 * conn.prepareStatement(sql); setSelectPS(statement, baseVo); // 要执行的SQL语句
	 * ResultSet rs = statement.executeQuery(); List<Object> obj =
	 * getSelectData(rs); closeConn(conn); return obj; }
	 */
}
