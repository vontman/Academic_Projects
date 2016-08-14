package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class InsertAuto implements Expression{

	private final String PATTERN;
	public InsertAuto() {
		PATTERN = "\\s*INSERT\\s+INTO\\s+([a-z0-9@#$_]+)\\s+VALUES\\s*\\(([^)]+)\\)\\s*\\s*";
	}

	public Pattern getCompiler(String regex){
		return Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	}
	public boolean interpret(String in){
		return getCompiler(PATTERN).matcher(in).matches();
	}
	@Override
	public LinkedList<String> getData(String in) {
		Pattern compiler = getCompiler(PATTERN);
		Matcher matcher = compiler.matcher(in);
		LinkedList<String> ret = new LinkedList<String>();
		compiler = getCompiler(PATTERN);
		matcher = compiler.matcher(in);
		if(matcher.matches()){
			ret.add(matcher.group(1).trim());
			String temp[] = matcher.group(2).split(",");
			for(int i = 0 ; i < temp.length ; i++)ret.add(temp[i].trim());
			return ret;
		}
		return null;
	}

}
