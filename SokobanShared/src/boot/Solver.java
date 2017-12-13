package boot;

import java.util.List;

import heuristics.Distance;
import model.data.positions.Position;
import plannable.MoveAction;
import plannable.Plannable;
import plannable.PlannerAction;
import plannable.SokobanPlannable;
import planners.Planner;
import planners.Strips;
import search.Searcher;
import search.Solution;

public class Solver {

	private String fileName;
	Searcher<Position> boxToTargetSearcher;
	Searcher<Position> playerToBoxSearcher;
	Distance disatnce;
	public Solver(String fileName, Searcher<Position> boxToTargetSearcher, Searcher<Position> playerToBoxSearcher,Distance distance) {
		this.fileName = fileName;
		this.boxToTargetSearcher = boxToTargetSearcher;
		this.playerToBoxSearcher = playerToBoxSearcher;
		this.disatnce = distance;
	}
	
	public Solution solve(){
		
		Plannable plannable = new SokobanPlannable(fileName, boxToTargetSearcher, playerToBoxSearcher, disatnce);
		Planner planner = new Strips();
		
		
		List<PlannerAction> plannerSolution = planner.plan(plannable);
		Solution solution = new Solution();
		
		if(plannerSolution != null){
			for(PlannerAction plannerAction : plannerSolution){
				solution.add(((MoveAction)plannerAction).getResults());
			}
		}
		
		return solution;
	}
}
