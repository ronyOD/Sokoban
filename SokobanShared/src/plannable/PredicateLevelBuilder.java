package plannable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.data.directions.Direction;
import model.data.directions.Down;
import model.data.directions.Left;
import model.data.directions.Right;
import model.data.directions.Up;
import model.data.levels.Level;
import model.data.loaders.LevelLoader;
import model.data.loaders.LevelLoaderException;
import model.data.loaders.LevelLoaders;
import model.data.positions.Position;
import plannable.PlannerAction;
import plannable.Clause;
import plannable.Plannable;
import plannable.Predicate;
import search.Searchable;
import search.Searcher;
import search.State;
import search.SokobanSearchable;
import search.Solution;

public class PredicateLevelBuilder {
	
/*	private Searcher<Position> searcher;
	
	public void setSearcher(Searcher<Position> searcher){
		this.searcher = searcher;
	}

	public Searcher<Position> getSearcher(){
		return null;
	}*/
	public static Clause getGoal(Clause kb){
		Clause goal=new Clause(null);
		for(Predicate p : kb.getPredicates()){
			if(p.getType().startsWith("tar")){
				goal.add(new Predicate("boxAt", "?", p.getValue()));
			}
		}
		return goal;	
	}
	
	public static Clause getKB(ArrayList<char[]> level){
		Clause kb=new Clause(null);
		int boxCount=0;
		int targetCount=0;
		for(int i=0;i<level.size();i++){
			for(int j=0;j<level.get(i).length;j++){
				switch(level.get(i)[j]){
				case '#':kb.add(new Predicate("wallAt", "", i+","+j));break;
				case ' ':kb.add(new Predicate("clearAt", "", i+","+j));break;
				case 'A':kb.add(new Predicate("sokobanAt", "", i+","+j));break;
				case 'b':boxCount++;kb.add(new Predicate("boxAt", "b"+boxCount, i+","+j));break;
				case '@':targetCount++;kb.add(new Predicate("targetAt", "t"+targetCount, i+","+j));break;
				}
			}
		}
		return kb;		
	}
	
	public static Level getLevel(String fileName){
		LevelLoader loader;
		try {
			loader = LevelLoaders.choose(fileName);
			Level sokobanLevel = loader.load(new FileInputStream(fileName));
			return sokobanLevel;
		} catch (LevelLoaderException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Plannable readFile(String fileName, Searcher<Position> searcher){
		
		try{
			Level sokobanLevel = getLevel(fileName);
			ArrayList<char[]> level=new ArrayList<>();
			BufferedReader in=new BufferedReader(new FileReader(fileName));
			String line;
			while((line=in.readLine())!=null){
				level.add(line.toCharArray());
			}
			in.close();
			Clause kb=getKB(level);
			Clause goal=getGoal(kb); 
			Plannable plannable=new Plannable() {
				
				@Override
				public Set<PlannerAction> getsatisfyingActions(Predicate top) {
					// TODO how to sort? should i sort?
					
					return null;
				}
				
				@Override
				public PlannerAction getSatisfyingAction(Predicate top, Clause kb) {
					
					try {
						
					// get all possible satisfying actions and sort by shortest path - BFS
					if(top.getType().startsWith("box")){
						
						//box to target
						String value = top.getValue(); //value "x,y"
						int x = Character.getNumericValue(value.charAt(0));
						int y = Character.getNumericValue(value.charAt(value.length()-1));
						//Set<Position> actions = sokobanLevel.getSatisfyingPositions
						HashMap<Position, Solution> boxToTargetSolutions = new HashMap<>(); //key - box position, value - solution.
						for(Position position : sokobanLevel.getBoxes()){
							SokobanSearchable sa = new SokobanSearchable(sokobanLevel);
							//setting initial & goal states
							sa.setInitialState(position);
							sa.setGoalState(new Position(x,y));
							//search path from box to target
							Solution solution = searcher.search(sa);
							if(solution != null){
								boxToTargetSolutions.put(position, solution);
								
							}
						}
						//solutions holds all existing paths from all boxes instances to specified target
						if(boxToTargetSolutions != null){
							validateSolutions(boxToTargetSolutions);
						}
						
						//player to box
						HashMap<Position, Solution> playerToBoxSolutions = new HashMap<>();
						for(Map.Entry<Position, Solution> entry : boxToTargetSolutions.entrySet()){
							SokobanSearchable sa = new SokobanSearchable(sokobanLevel);
							sa.setInitialStatePlayerPosition();
							//get goal state
							Direction direction = getLastActionDirection(getLastAction(entry.getValue()));
							Position goalPosition = Level.nextPosition(entry.getKey(), Level.oppositeDirection(direction));
							sa.setGoalState(goalPosition);
							Solution solution = searcher.search(sa);
							if(solution != null){
								playerToBoxSolutions.put(entry.getKey(), solution);
							}	
						}
					}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					return null;
				}
				
				@Override
				public Clause getKnowledgebase() {
					return kb;
				}
				
				@Override
				public Clause getGoal() {
					return goal;
				}
				
				public void validateSolutions(HashMap<Position, Solution> solutions){
					if(solutions != null){
						for(Map.Entry<Position, Solution> entry : solutions.entrySet()){
							//examine last step - indicates where to place sokoban player
							Position position = entry.getKey();
							Solution solution = entry.getValue();
							switch(getLastAction(solution).getName()){
							case "Move Up":
								//check if surrounding box positions are vacant
								if(!sokobanLevel.checkNeighboringPositions(position, new Up())){
									solutions.remove(position);
								}
								break;
							case "Move Down":
								if(!sokobanLevel.checkNeighboringPositions(position, new Down())){
									solutions.remove(position);
								}
								break;
							case "Move Left":
								if(!sokobanLevel.checkNeighboringPositions(position, new Left())){
									solutions.remove(position);
								}
								break;
							case "Move Right":
								if(!sokobanLevel.checkNeighboringPositions(position, new Right())){
									solutions.remove(position);
								}
								break;
							}
						}
					}
					//return solutions;
				}
				
				public search.Action getLastAction(Solution solution){
					search.Action action = solution.getActions().get(solution.getActions().size()-1);
					return action;
				}
				
				public Direction getLastActionDirection(search.Action lastAction){
					String direction = lastAction.getName().split(" ")[1];
					if(direction == "Up"){
						return new Up();
					}
					else if(direction == "Down"){
						return new Down();
					}
					else if(direction == "Left"){
						return new Left();
					}
					else {
						return new Right();
					}
				}
			};
			return plannable;
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
