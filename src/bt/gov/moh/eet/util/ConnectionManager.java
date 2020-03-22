
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bt.org.crs.framework.common.logger.Log;

public class ConnectionManager {
	
	private static DataSource rmaDataSource = null;
	private static final String DATASOURCE_NAME_RMADB = "java:comp/env/jdbc/EETDB";

	public static DataSource getDatasourceName(String dataSourceName) {

		try {
			InitialContext ctx = new InitialContext();
			Log.debug("dataSourceName : " + dataSourceName);
			if (dataSourceName != null && !dataSourceName.equals("")) {
				try {
					rmaDataSource = (DataSource) ctx.lookup(dataSourceName);
				} catch (Exception e) {
					Log.error(" Exception occured within if block of getDatasourceName method ", e);
				}
			}
		} catch (NamingException e) {
			Log.error(" NamingException occured in getDatasourceName method ", e);
		}
		return rmaDataSource;
		
	}
	
	public static final Connection getConnection() {
		return getConnection(DATASOURCE_NAME_RMADB);
	}

	public static final Connection getConnection(String dataSourceName) {
		
		Connection conn = null;
		try {
			rmaDataSource = getDatasourceName(dataSourceName);
			Log.debug(" the datasource object is " + rmaDataSource.toString());
			if (rmaDataSource == null) {
				Log.debug(" Problem %%%%%%%%%%%%%%%%%%%%% ");
			} else {
				conn = rmaDataSource.getConnection();
				Log.debug(" the connection object is " + conn.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.error(" SQLException occured in getConnection method ", e);
		} catch (Exception e) {
			Log.error(" Exception occured  in getConnection method ", e);
		}
		return conn;
		
	}
	
	/**
	 * Returns all JDBC resources associated with the given connection to to the
	 * pool. All exceptions are handled by being logged. Should be called in
	 * 'finally{}' clause wherever JDBC connections are used.
	 * 
	 * @param conn
	 *            connection to be closed, or null
	 * @param stmt
	 *            statement to be closed, or null
	 * @param rset
	 *            ResultSet to be closed, or null
	 */
	public static void close(Connection conn, ResultSet rs, PreparedStatement pst)
	{
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(reset) of close method  ", ex);
			}
		}
		if (pst != null) {
			try {
				pst.close();
				pst = null;
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(ps) of close method  ", ex);
			}
		}

		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(conn) of close method  ", ex);
			}
		}
	}

	/**
	 * Returns all JDBC resources associated with the given connection to to the
	 * pool. All exceptions are handled by being logged. Should be called in
	 * 'finally{}' clause wherever JDBC connections are used.
	 * 
	 * @param conn
	 *            connection to be closed, or null
	 */
	public static void close(Connection conn)
	{
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(conn) of close method  ", ex);
			}
		}
	}
}
