package eg.edu.alexu.csd.oop.jdbc;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;


public class MyDriver implements Driver{
	private MyConnection connection;
	@Override
	public boolean acceptsURL(String arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public Connection connect(String s, Properties prop) throws SQLException {
		File temp = (File) prop.get("path");
		String path = temp.getPath();
		connection = new  MyConnection(path);
		return connection;
	}
////////////////////////////
	@Override
	public int getMajorVersion() {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getMinorVersion() {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1)
			throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public boolean jdbcCompliant() {
		throw new java.lang.UnsupportedOperationException(); 
	}
	
}
