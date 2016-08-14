package eg.edu.alexu.csd.oop.jdbc;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.ResultInfo;

public interface DatabaseHandler extends Database{
	public void setPath(String path);
	public ResultInfo selectQuery(String query) throws SQLException;
}
