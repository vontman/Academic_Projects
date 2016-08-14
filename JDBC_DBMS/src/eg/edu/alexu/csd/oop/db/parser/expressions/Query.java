package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class Query implements Expression{

	Expression select = new Select();
	 public Query() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean interpret(String in) {
		return select.interpret(in);
	}
	@Override
	public LinkedList<String> getData(String in) {
		if(select.interpret(in))
			return select.getData(in);
		return null;
		
	}

	

}
