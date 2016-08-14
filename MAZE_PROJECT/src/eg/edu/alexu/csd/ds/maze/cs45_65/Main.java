package eg.edu.alexu.csd.ds.maze.cs45_65;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	static boolean readFile(){
		BufferedReader br;
		int i=1;
		try {
			br = new BufferedReader(new FileReader("input.txt"));
			String temp = br.readLine(); 
			String tempMaze[] = new String[500];
			String[] numbers = temp.split(" ");
			length = Integer.parseInt(numbers[0]);
			width = Integer.parseInt(numbers[1]);
			tempMaze[0] = br.readLine();      				
			while(tempMaze[i-1]!=null){
				tempMaze[i] = br.readLine();
				i++;
			}
			i--;
			br.close();
			maze = Arrays.copyOfRange(tempMaze, 0, length);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	static Point[][] hamada = new Point[100][100];
	static MyStack invert = new Stack();
	static String [] maze = null;
	static int  length, width;

	public static void main(String[] args){
		try{
			if(!readFile())maze = null;
			GUI myGUI = new GUI(maze);
			myGUI.Start();
		}catch(Exception ex){
			System.out.println(ex);
		}
	}	
}