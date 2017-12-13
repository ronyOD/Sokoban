package boot;

import heuristics.ManhattanDistance;
import model.data.positions.Position;
import search.BFS;
import search.Solution;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solver solver = new Solver("level2.txt", new BFS<Position>(), new BFS<Position>(), new ManhattanDistance());
		Solution solution = solver.solve();
		System.out.println(solution.toString());
	}

}
