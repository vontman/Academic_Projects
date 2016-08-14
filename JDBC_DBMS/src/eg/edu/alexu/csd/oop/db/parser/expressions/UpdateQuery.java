package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class UpdateQuery implements Expression{

	Expression upd = new Update();
	Expression delete = new Delete();
	Expression insert = new Insert();
	public UpdateQuery() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean interpret(String in) {
		return upd.interpret(in) || delete.interpret(in) || insert.interpret(in);
	}
	@Override
	public LinkedList<String> getData(String in) {
		if(upd.interpret(in))
			return upd.getData(in);
		
		if(delete.interpret(in))
			return delete.getData(in);
		
		if(insert.interpret(in))
			return insert.getData(in);
		return null;
		
	}

	

}
