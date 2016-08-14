package eg.edu.alexu.csd.oop.db.parser.expressions;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.parser.Expression;

public class Update implements Expression{

	private final String wherePtrn = "WHERE\\s+(.+\\s*[=<>]\\s*.+)\\s*\\s*";
	private final String PATTERN_1,PATTERN_2;
	public Update() {
		PATTERN_1 = "\\s*UPDATE\\s+([a-z0-9@#$_]+)\\s+SET\\s+(.+)\\s+"+wherePtrn;
		PATTERN_2 = "\\s*UPDATE\\s+([a-z0-9@#$_]+)\\s+SET\\s+(.+\\s*[\\=|\\<|\\>]\\s*.+)\\s*\\s*";
	}
	public Pattern getCompiler(String regex){
		return Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	}
	public boolean interpret(String in){
		return  getCompiler(PATTERN_1).matcher(in).matches() || getCompiler(PATTERN_2).matcher(in).matches();
	}
	@Override
	public LinkedList<String> getData(String in) {
		Pattern compiler = getCompiler(PATTERN_1);
		Matcher matcher = compiler.matcher(in);
		LinkedList<String> ret = new LinkedList<String>();
		if( matcher.matches() ){
			ret.add(matcher.group(1).trim());
			ret.add(matcher.group(2).trim());
			ret.add(matcher.group(3).trim());
			return ret;
		}
		compiler = getCompiler(PATTERN_2);
		matcher = compiler.matcher(in);
		if(matcher.matches()){
			ret.add(matcher.group(1).trim());
			ret.add(matcher.group(2).trim());
			ret.add(null);
			return ret;
		}
		return null;
	}

}
