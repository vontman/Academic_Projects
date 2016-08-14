package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;

import eg.edu.alexu.csd.oop.db.parser.Interpretter;

public class Control implements Database {
	protected String path = "";
	private String last = null;
	private Interpretter interpretter = null;
	private XmlHandler handler = null;
	public Control() {
		last = null;
		interpretter = Interpretter.getInst();
		handler = new XmlHandler();
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists){
		databaseName = databaseName.toLowerCase();
		if(dropIfExists){
			try {
				executeStructureQuery(String.format("Drop database %s", databaseName));
				executeStructureQuery(String.format("Create database %s", databaseName));
			} catch (SQLException e) {
				e.printStackTrace();
				return last;
			}
		}
		else {
			try {
				executeStructureQuery(String.format("Create database %s", databaseName));
			} catch (SQLException e) {
				e.printStackTrace();
				return last;
			}
		}
		return last;
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		query = query.toLowerCase();
		if( !interpretter.checkStructureQuery(query) )
			throw new SQLException();
		LinkedList<String> ret = interpretter.getDataStructureQuery(query);
		String tableName = ret.removeFirst().trim();
		if (interpretter.checkCreateDB(query) || interpretter.checkDeleteDB(query)) {
			File file = new File(path+tableName);
			if (file.exists() && interpretter.checkDeleteDB(query)) {
				deleteFolder(file);
			} else if (!file.exists())
				file.mkdirs();
			last = file.getPath();
			return true;
		} 
		if (last == null)
			throw new SQLException();
		if (interpretter.checkCreateTable(query)) {
			return handler.createXml(tableName,last+File.separator+tableName, ret.removeLast().split("\\s*,\\s*"));
		} else if (interpretter.checkDeleteTable(query)) {
			handler.dropTbl(last+File.separator+tableName);
			return true;
		}
		return false;
	}
	public void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		return selectQuery(query).getResultArr();
	}
	public ResultInfo selectQuery(String query) throws SQLException{
		query = query.toLowerCase();
		if (last == null)
			throw new SQLException();
		if( !interpretter.checkQuery(query) )
			throw new SQLException();
		LinkedList<String> ret = interpretter.getDataQuery(query);
		String tableName = ret.removeFirst();
		String condition = ret.removeLast();
		String[] selection = ret.removeLast().split("\\s*,\\s*");
		for(int i=0;i<selection.length;i++){
			selection[i] = selection[i].trim();
		}
		ResultInfo myResult= handler.select(selection,last + File.separator + tableName, condition);
		myResult.setTableName(tableName);
		
		return myResult;
	}


	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		query = query.toLowerCase();
		
		if (last == null)
			throw new SQLException();
		if(!interpretter.checkUpdateQuery(query))
			throw new SQLException();

		LinkedList<String> ret = interpretter.getDataUpdateQuery(query);
		String tableName = ret.removeFirst();
		
		if (interpretter.checkInsert(query)) {
			String[] names = new String[ret.size()];
			ret.toArray(names);
			return handler.insertNode(last + File.separator + tableName, names);
		} else if (interpretter.checkUpdate(query)) {
			String condition = ret.removeLast();
			String[] selections = ret.removeLast().split("\\s*,\\s*");
			for(int i=0;i<selections.length;i++){
				selections[i] = selections[i].trim();
			}
			return handler.update(last + File.separator + tableName, selections, condition);

		} else if (interpretter.checkDelete(query)) {
			String condition = ret.removeLast();
			return handler.deleteRange(last + File.separator + tableName, condition);
		}
		return 0;
	}

}