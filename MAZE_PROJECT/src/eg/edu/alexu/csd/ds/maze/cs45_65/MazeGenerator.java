package eg.edu.alexu.csd.ds.maze.cs45_65;

import java.util.Random;

public class MazeGenerator {
	int maze_width , maze_height;
	int width, height;
	
	public MazeGenerator(int h , int w) {
		maze_height = h ;
		maze_width = w;
		width = maze_width*2+1;
		height = maze_height*2+1;
	}

	public String[] generate(){
		MyStack moves  = new Stack();
		Character [][]maze = new Character[height][width];
		String [] maze_str = new String[height];
		Random rand = new Random();
		char[] chars = new char[width];
		
		String possible_directions;
		
		for(int i=0;i<height;i++)
			for(int j=0;j<width;j++)
				maze[i][j]='#';
		
		int x_pos = 1;
		int y_pos = 1;int move;
		maze[x_pos][y_pos]='.';
		moves.push(y_pos+(x_pos*width));
		while(!moves.isEmpty()){
		     possible_directions = "";
		     if(x_pos < height-2){
			     if(maze[x_pos+2][y_pos]=='#' && x_pos+2!=0 && x_pos+2!=height-1)
			          possible_directions += "S";
			 }
		     if(x_pos > 1){
			     if(maze[x_pos-2][y_pos]=='#' && x_pos-2!=0 && x_pos-2!=height-1)
			          possible_directions += "N";
			 }
		     if(y_pos > 1){
			     if(maze[x_pos][y_pos-2]=='#' && y_pos-2!=0 && y_pos-2!=width-1)
			          possible_directions += "W";
		     }
		     if(y_pos < width-2){
			     if(maze[x_pos][y_pos+2]=='#' && y_pos+2!=0 && y_pos+2!=width-1)
			          possible_directions += "E";
		     }
		     if(possible_directions.length()>0){
		          move = rand.nextInt(possible_directions.length());
		          switch (possible_directions.charAt(move)){
		               case 'N': maze[x_pos-2][y_pos]='.';
		                         maze[x_pos-1][y_pos]='.';
		                         x_pos -=2;
		                         break;
		               case 'S': maze[x_pos+2][y_pos]='.';
		                         maze[x_pos+1][y_pos]='.';
		                         x_pos +=2;
		                         break;
		               case 'W': maze[x_pos][y_pos-2]='.';
		                         maze[x_pos][y_pos-1]='.';
		                         y_pos -=2;
		                         break;
		               case 'E': maze[x_pos][y_pos+2]='.';
		                         maze[x_pos][y_pos+1]='.';
		                         y_pos +=2;
		                         break;        
		          }
		          moves.push(y_pos+(x_pos*width));
		     }
		     else{
		          int back = (int)moves.pop();
		          x_pos = back/width;
		          y_pos = back%width;
		     }
		}
		int randomX  = rand.nextInt(height);
		int randomY = rand.nextInt(width);
		while(maze[randomX][randomY]=='#'){
			randomX  = rand.nextInt(height);
			randomY = rand.nextInt(width);
		}
		maze[randomX][randomY]='S';
		while(maze[randomX][randomY]=='#' || maze[randomX][randomY]=='S'){
			randomX  = rand.nextInt(height);
			randomY = rand.nextInt(width);
			if(randomX + 1 < height){
				if(maze[randomX+1][randomY] == 'S')continue;
			}
			if(randomX - 1 >= 0){
				if(maze[randomX-1][randomY] == 'S')continue;
			}
			if(randomY + 1 < width){
				if(maze[randomX][randomY+1] == 'S')continue;
			}
			if(randomY - 1 >= 0){
				if(maze[randomX][randomY-1] == 'S')continue;
			}
		}
		maze[randomX][randomY]='E';
		while(maze[randomX][randomY]!='.'){
			randomX  = rand.nextInt(height);
			randomY = rand.nextInt(width);
		}
		maze[randomX][randomY]='*';
		while(maze[randomX][randomY]!='.'){
			randomX  = rand.nextInt(height);
			randomY = rand.nextInt(width);
			if(randomX + 1 < height){
				if(maze[randomX+1][randomY] == '*')continue;
			}
			if(randomX - 1 >= 0){
				if(maze[randomX-1][randomY] == '*')continue;
			}
			if(randomY + 1 < width){
				if(maze[randomX][randomY+1] == '*')continue;
			}
			if(randomY - 1 >= 0){
				if(maze[randomX][randomY-1] == '*')continue;
			}
		}
		maze[randomX][randomY]='*';

		for(int i=0 ; i<height;i++){
			for(int j=0 ; j<width;j++)
				chars[j]=maze[i][j];
			maze_str[i] = String.valueOf(chars);
		}
	return maze_str;
	}
}