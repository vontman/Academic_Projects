package eg.edu.alexu.csd.oop.db.parser;

import java.sql.SQLClientInfoException;
import java.util.LinkedList;

import eg.edu.alexu.csd.oop.db.parser.expressions.CreateDatabase;
import eg.edu.alexu.csd.oop.db.parser.expressions.CreateTable;
import eg.edu.alexu.csd.oop.db.parser.expressions.Delete;
import eg.edu.alexu.csd.oop.db.parser.expressions.DeleteDatabase;
import eg.edu.alexu.csd.oop.db.parser.expressions.DeleteTable;
import eg.edu.alexu.csd.oop.db.parser.expressions.Insert;
import eg.edu.alexu.csd.oop.db.parser.expressions.Query;
import eg.edu.alexu.csd.oop.db.parser.expressions.Select;
import eg.edu.alexu.csd.oop.db.parser.expressions.StructureQuery;
import eg.edu.alexu.csd.oop.db.parser.expressions.Update;
import eg.edu.alexu.csd.oop.db.parser.expressions.UpdateQuery;

public class Interpretter {
	private static Interpretter inst ;
	public static Interpretter getInst(){
		if(inst == null)
			inst = new Interpretter();
		return inst;
	}
	private Expression updateQuery;
	private Expression query;
	private Expression structureQuery;
	private Expression updateEx;
	private Expression deleteEx;
	private Expression selectEx;
	private Expression insertEx;
	private Expression createDbEx;
	private Expression deleteDbEx;
	private Expression createTblEx;
	private Expression deleteTblEx;
	
	private Interpretter() {
		updateEx = new Update();
		deleteEx = new Delete();
		selectEx = new Select();
		insertEx = new Insert();
		createDbEx = new CreateDatabase();
		deleteDbEx = new DeleteDatabase();
		createTblEx = new CreateTable();
		deleteTblEx = new DeleteTable();
		query = new Query();
		structureQuery = new StructureQuery();
		updateQuery = new UpdateQuery();
	}
	public boolean checkUpdateQuery(String in){
		return updateQuery.interpret(in);
	}
	public boolean checkQuery(String in){
		return query.interpret(in);
	}
	public boolean checkStructureQuery(String in){
		return structureQuery.interpret(in);
	}
	public LinkedList<String> getDataUpdateQuery(String in) throws SQLClientInfoException{
		return updateQuery.getData(in);
	}
	public LinkedList<String> getDataQuery(String in) throws SQLClientInfoException{
		return query.getData(in);
	}
	public LinkedList<String> getDataStructureQuery(String in) throws SQLClientInfoException{
		return structureQuery.getData(in);
	}
	public boolean checkUpdate(String in){
		return updateEx.interpret(in);
	}
	public boolean checkDelete(String in){
		return deleteEx.interpret(in);
	}
	public boolean checkSelect(String in){
		return selectEx.interpret(in);
	}
	public boolean checkInsert(String in){
		return insertEx.interpret(in);
	}
	public boolean checkCreateDB(String in){
		return createDbEx.interpret(in);
	}
	public boolean checkCreateTable(String in){
		return createTblEx.interpret(in);
	}
	public boolean checkDeleteDB(String in){
		return deleteDbEx.interpret(in);
	}
	public boolean checkDeleteTable(String in){
		return deleteTblEx.interpret(in);
	}
}
