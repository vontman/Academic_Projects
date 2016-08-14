package eg.edu.alexu.csd.ds.maze.cs45_65;

import java.awt.Point;

public interface MazeSolver {
//	void useAlgorithm();
	public boolean solve();
	public Point[] getPath();
	public void getData(String[] target, int targetx, int targety);
}
