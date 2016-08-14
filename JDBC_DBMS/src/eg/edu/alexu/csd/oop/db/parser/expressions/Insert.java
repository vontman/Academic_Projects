package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class Insert implements Expression{

	Expression insertNorm = new InsertNormal();
	Expression insertAuto = new InsertAuto();
	public Insert() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean interpret(String in) {
		return insertAuto.interpret(in) || insertNorm.interpret(in);
	}
	@Override
	public LinkedList<String> getData(String in) {
		if(insertNorm.interpret(in))
			return insertNorm.getData(in);
		
		if(insertAuto.interpret(in))
			return insertAuto.getData(in);
		return null;
		
	}


}
