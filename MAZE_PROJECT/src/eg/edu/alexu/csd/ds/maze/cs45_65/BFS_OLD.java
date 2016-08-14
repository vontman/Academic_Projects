package eg.edu.alexu.csd.ds.maze.cs45_65;

import java.awt.Point;

public class BFS_OLD implements MazeSolver {
	
	public void getData(String[] target, int targetx, int targety) {
		x = targetx;  y = targety;
		maze = target;
		n=maze.length;
		m= maze[0].length();
	}
	int x, y, n, m, cx, cy;
	int di[]={1,-1, 0, 0};
	int dj[]={0, 0, 1, -1};
	String [] maze;
	boolean flag = false;
	Point destination = new Point(-1, -1);
	Point temp;
	boolean [][]visited = new boolean[500][500];
	Point [][] prev = new Point[500][500];
	MyQueue points  = new LLQueue();
	
	boolean check(int i, int j){
		if( i==n || i<0 || j==m || j<0 || visited[i][j] || maze[i].charAt(j)=='#') return false;
		return true;
	}
	
	public boolean solve(){
		Point swapPosX = null;
		Point swapPosY = null;
		for(int i=0;i<maze.length;i++){
			for(int j=0;j<maze[i].length();j++){
				if(maze[i].charAt(j) != '*')continue;
				if( swapPosX == null )swapPosX = new Point(i,j);
				else swapPosY = new Point(i,j);
			}
		}
		visited = new boolean[500][500];
		prev[x][y] = new Point(-1,-1);
		points.enqueue(new Point(x, y));
		visited[x][y]=true;
		while (!points.isEmpty()){
			temp = (Point)points.dequeue();
			cx=temp.x; cy=temp.y	;
			for(int k=0 ; k<4 ; k++){
				if (check(cx+di[k], cy+dj[k])){
					visited[cx+di[k]][cy+dj[k]] = true;
					prev[cx+di[k]][cy+dj[k]] = new Point(cx, cy);
					points.enqueue(new Point(cx+di[k], cy+dj[k]));
					if(maze[cx+di[k]].charAt(cy+dj[k])=='E'){
						destination.x = cx+di[k]; destination.y = cy+dj[k];
						return true;
					}
					if(swapPosX == null || swapPosY == null || maze[cx+di[k]].charAt(cy+dj[k]) != '*')continue;
					if(swapPosX.equals(new Point( cx+di[k], cy+dj[k] ))){
						if(!visited[swapPosY.x][swapPosY.y]){
							visited[swapPosY.x][swapPosY.y] = true;
							prev[swapPosY.x][swapPosY.y] = new Point(cx, cy);
							points.enqueue(swapPosY);
						}
					}else if(swapPosY.equals(new Point( cx+di[k], cy+dj[k] ))){
						if(!visited[swapPosX.x][swapPosX.y]){
							visited[swapPosX.x][swapPosX.y] = true;
							prev[swapPosX.x][swapPosX.y] = new Point(cx, cy);
							points.enqueue(swapPosX);
						}
					}
				}	
			}
		}
		return false;
	}

	@Override
	public Point[] getPath() {
		if(destination == null)return null;
		MyStack path = new Stack();
		Point retPath[] = new Point[200];
		Point temp = destination;
		int i=0;
		while( temp.x != -1 ){
			path.push(temp);
			temp = prev[temp.x][temp.y];
		}
		while(!path.isEmpty()){
			retPath[i++] = (Point)path.pop();
		}
		retPath[i] = new Point(-2,-2);
		return retPath;
	}
}
