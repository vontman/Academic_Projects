package eg.edu.alexu.csd.oop.jdbc;

import java.io.File;

import eg.edu.alexu.csd.oop.db.Control;

public class MyDatabaseHandler extends Control implements DatabaseHandler{
	public  void setPath(String path){
		this.path = path+File.separator;
	}
}
