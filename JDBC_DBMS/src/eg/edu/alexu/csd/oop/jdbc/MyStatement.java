package eg.edu.alexu.csd.oop.jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Queue;

import eg.edu.alexu.csd.oop.db.ResultInfo;


public class MyStatement implements Statement{
    private Queue<String> statementQeue;
    private MyConnection connectionEngine;
	public MyStatement(MyConnection c) {
		connectionEngine=c;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	}

	@Override
	public void addBatch(String s) throws SQLException {
		statementQeue.add(s);
	}

	@Override
	public void cancel() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void clearBatch() throws SQLException {
		statementQeue.clear();
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void close() throws SQLException {
		 connectionEngine=null;
		
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public boolean execute(String s) throws SQLException {
		boolean flag = false;
		boolean ret = false;
		try{
			ret =  connectionEngine.getDBMS().executeStructureQuery(s);
			flag = true;
		}catch(Exception ex){
			flag = false;
		}	
		if(flag)return ret;
		try{
			ret =  connectionEngine.getDBMS().executeUpdateQuery(s) > 0;
			if(ret)
				flag = true;
		}catch(Exception ex){
			flag = false;
		}	
		if(flag)return ret;	
		System.out.println(Arrays.deepToString(connectionEngine.getDBMS().executeQuery(s)));
		return connectionEngine.getDBMS().executeQuery(s).length>0;	
		
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public int[] executeBatch() throws SQLException {
		int []updateNo= new int[statementQeue.size()];
		for(int i=0;i<statementQeue.size();i++){
			String temp=statementQeue.peek();
			statementQeue.poll();
			statementQeue.add(temp);
			 updateNo[i]=connectionEngine.getDBMS().executeUpdateQuery(temp);
		}
		return updateNo;
	}

	@Override
	public ResultSet executeQuery(String s) throws SQLException {
		ResultInfo res = connectionEngine.getDBMS().selectQuery(s);
		ResultSet set= new MyResultSet(this,res);
		return set;
	}

	@Override
	public int executeUpdate(String s) throws SQLException {
		return connectionEngine.getDBMS().executeUpdateQuery(s);
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	
	}

	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	
	}

	@Override
	public Connection getConnection() throws SQLException {
		return connectionEngine;
		
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	
	}

	@Override
	public int getMaxRows() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getResultSetType() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public int getUpdateCount() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
	
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public boolean isPoolable() throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		 throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		throw new java.lang.UnsupportedOperationException(); 
		
	}

}
