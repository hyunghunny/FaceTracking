package kr.ac.snu.ads.facetracking;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FaceDetectingDB  {
	
	private Connection connection = null;
	private String currentTable = null;
	public FaceDetectingDB(String dbName) throws Exception {

			Class.forName("org.sqlite.JDBC");
			String dbUrl = "jdbc:sqlite:" + dbName + ".db";
			connection = DriverManager.getConnection(dbUrl);
	}
	
	public boolean createTable(String tableName) {
		try {
			currentTable = tableName;
			Statement statement = connection.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    String query = "CREATE TABLE IF NOT EXISTS " + 
		    		tableName +
		    		" (id INT, " +
		    		" joined DATETIME, leaved DATETIME)";
		    int nRet = statement.executeUpdate(query);
		    return true;
		    
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void setTable(String tableName) {
		currentTable = tableName;
	}
	
	public boolean insert(UserPresence presence) {
		try {
			int id = presence.getUserId();
			long joined = presence.getJoinedDate().getTime();
			long leaved = presence.getLeavedDate().getTime();;
			Statement statement = connection.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    String query = "INSERT INTO " + 
		    		currentTable + "(id, joined, leaved)" +
		    		" VALUES(" + id + ", " +
		    		joined + ", " + 
		    		leaved + ")";
		    int nRet = statement.executeUpdate(query);
		    if (nRet > 0) {
		    	return true;
		    } else {
		    	System.out.println("Insert failed");
		    	return false;
		    }
		    
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
}
