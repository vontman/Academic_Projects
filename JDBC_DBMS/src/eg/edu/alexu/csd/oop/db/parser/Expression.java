package eg.edu.alexu.csd.oop.db.parser;

import java.util.LinkedList;

public interface Expression {
	public boolean interpret(String in);
	public LinkedList<String> getData(String in);
}
