package boot;

import java.util.List;

import heuristics.ManhattanDistance;
import model.data.positions.Position;
import plannable.MoveAction;
import plannable.Plannable;
import plannable.PlannerAction;
import plannable.SokobanPlannable;
import planners.Planner;
import planners.Strips;
import search.BFS;
import search.Solution;

public class SolveDemo {

	public static void main(String[] args) {
		
		Plannable plannable = new SokobanPlannable("level1.txt", new BFS<Position>(), new BFS<Position>(), new ManhattanDistance());
		Planner planner = new Strips();
		List<PlannerAction> plannerSolution = planner.plan(plannable);
		Solution solution = new Solution();
		
		if(plannerSolution != null){
			for(PlannerAction plannerAction : plannerSolution){
				solution.add(((MoveAction)plannerAction).getResults());
			}
		}
		
		System.out.println(plannerSolution.toString());
		System.out.println(solution.toString());

	}

}
