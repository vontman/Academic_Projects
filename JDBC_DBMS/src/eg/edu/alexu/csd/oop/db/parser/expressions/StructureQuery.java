package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class StructureQuery implements Expression{

	Expression createTbl = new CreateTable();
	Expression deleteTbl= new DeleteTable();
	Expression createDb = new CreateDatabase();
	Expression deleteDb = new DeleteDatabase();
	public StructureQuery() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean interpret(String in) {
		return createTbl.interpret(in) || deleteTbl.interpret(in) || createDb.interpret(in) || deleteDb.interpret(in);
	}
	@Override
	public LinkedList<String> getData(String in) {
		if(createTbl.interpret(in))
			return createTbl.getData(in);
		if(deleteTbl.interpret(in))
			return deleteTbl.getData(in);
		if(createDb.interpret(in))
			return createDb.getData(in);
		if(deleteDb.interpret(in))
			return deleteDb.getData(in);
		return null;
		
	}

	

}
