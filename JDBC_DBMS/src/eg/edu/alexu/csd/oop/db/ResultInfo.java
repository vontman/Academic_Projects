package eg.edu.alexu.csd.oop.db;


import java.sql.Types;
import java.util.LinkedList;

public class ResultInfo {
	private LinkedList<LinkedList<Object>> result;
	private LinkedList<String>resultCols;
	private LinkedList<Integer>resultType;
	private Object [][] resultArr;
	private String tableName;
	public ResultInfo(){
		
	}
	public Object[][] getResultArr() {
		return resultArr;
	}
	
	public String getTableName(){
		return this.tableName;
	}
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public int getColumnType( int col ){
		return resultType.get(col);
	}
	public LinkedList<String> getResultCols() {
		return resultCols;
	}

	public void setResult(Object [][]resultArr) {
		this.resultArr = resultArr;
		for(int i=0;i<resultArr.length;i++){
			for(int j=0;j<resultArr[i].length;j++){
				try{
					if(((String)resultArr[i][j]).charAt(0) == '\''){
						resultArr[i][j] = ((String)resultArr[i][j]).replaceAll("'", "");
					}else
						resultArr[i][j] = Integer.valueOf((String)resultArr[i][j]);
				}catch(Exception ex){
					
				}
			}
		}
		result = new LinkedList<LinkedList<Object>>();
		for(int i=0;i<resultArr.length;i++){
			LinkedList<Object>tempList = new LinkedList<Object>();
			for(int j=0;j<resultArr[i].length;j++){
				tempList.add(resultArr[i][j]);
			}
			result.add(tempList);
		}
	}
	public void setResultType(LinkedList<String> inTypes) {
		this.resultType = new LinkedList<Integer>();
		for(String s:inTypes){
			if("int".equals(s))this.resultType.add(Types.INTEGER);
			else this.resultType.add(Types.VARCHAR);
		}
	}
	public int getColsCount(){
		return this.resultCols.size();
	}
	public void setResultCols(LinkedList<String> resultCols) {
		this.resultCols = resultCols;
	}
	public LinkedList<LinkedList<Object>> getResultList(){
		return this.result;
	}
	public int getColIndex(String label){
		int counter=0;
		for(Object x: getResultCols()){
			if(x.equals(label))
				return counter;
			counter++;
		}
		return -1;
	}
}
