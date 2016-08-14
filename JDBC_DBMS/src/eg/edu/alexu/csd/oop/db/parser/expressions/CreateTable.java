package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class CreateTable implements Expression{

	private final String PATTERN;
	public CreateTable() {
		PATTERN = "\\s*CREATE\\s+TABLE\\s+([a-z0-9@#$_]+)\\s*\\((\\s*.+\\s+\\b(int|varchar)\\b\\s*+)\\)\\s*\\s*";
	}

	public Pattern getCompiler(String regex){
		return Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	}
	public boolean interpret(String in){
		return  getCompiler(PATTERN).matcher(in).matches() ;
	}
	@Override
	public LinkedList<String> getData(String in) {
		Pattern compiler = getCompiler(PATTERN);
		Matcher matcher = compiler.matcher(in);
		LinkedList<String> ret = new LinkedList<String>();
		if( matcher.matches() ){
			ret.add(matcher.group(1));
			ret.add(matcher.group(2));
			return ret;
		}
		return null;
	}

}
