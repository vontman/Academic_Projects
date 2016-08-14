package eg.edu.alexu.csd.oop.jdbc;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class jdbctest {

	@Test
	public void testCreateAndOpenAndDropDatabase() throws SQLException {
		File dummy = null;
		Driver driver = new MyDriver();
		Properties info = new Properties();
		File dbDir = new File("debug/db/test/sample");
		info.put("path", dbDir.getAbsoluteFile());
		Connection connection = driver.connect("jdbc:xmldb://localhost", info);
		{
			Statement statement = connection.createStatement();
			statement.execute("DROP Database SaMpLe");
			statement.execute("create Database SaMpLe");
			System.out.println(statement.execute("CREATE TABLE table_name13(column_name1 varchar, column_name2 int, column_name3 varchar)"));
			statement.executeUpdate("INSERT INTO table_name13(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
			statement.execute("INSERT INTO table_name13(column_NAME1, column_name2, COLUMN_name3) VALUES ('value1', 8, 'value3')");
			System.out.println(statement.execute("SELECT column_name1 FROM table_name13 WHERE coluMN_NAME2 = 8"));
			System.out.println(statement.execute("SELECT column_name1 FROM table_name13 WHERE coluMN_NAME2 > 100"));
			statement.executeQuery("select * from table_name13");
			dummy = new File(dbDir, "dummy");
			boolean flag = false;
			try {
				boolean created = dummy.createNewFile();
				Assert.assertTrue("Failed t create file into DB", created && dummy.exists());
			} catch (IOException e) {
				flag = true;
			}
			if(flag)
				fail("Failed t create file into DB");

			statement.execute("DROP Database SaMpLe");
			statement.close();
		}
		{
			Statement statement = connection.createStatement();
			statement.execute("CREATE Database sAmPlE");
			String files[] = dbDir.list();
			Assert.assertTrue("Database directory is empty after opening!", files.length > 0);
			Assert.assertTrue("Failed t create find a previously created file into DB", dummy.exists());
			statement.close();
		}
		{
			Statement statement = connection.createStatement();
			statement.execute("DROP Database SAMPLE");
			statement.execute("CREATE Database SAMPLE");
			String files[] = dbDir.list();
			Assert.assertTrue("Database directory is not empty after drop!", files == null || files.length == 0);
			statement.close();
		}
		connection.close();
	}
	
}	
