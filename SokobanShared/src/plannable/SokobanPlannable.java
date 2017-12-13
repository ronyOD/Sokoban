package plannable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

import heuristics.Distance;
import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Floor;
import model.data.cells.Target;
import model.data.cells.Wall;
import model.data.directions.Direction;
import model.data.levels.Level;
import model.data.loaders.LevelLoader;
import model.data.loaders.LevelLoaderException;
import model.data.loaders.LevelLoaders;
import model.data.positions.Position;
import model.policy.LevelPolicyException;
import search.Action;
import search.Searcher;
import search.SokobanBoxToTargetAdapter;
import search.SokobanSearchable;
import search.Solution;

public class SokobanPlannable implements Plannable {

	private Level level;
	private Level currentLevel;
	private HashMap<Position, String> boxesMap;
	private Searcher<Position> boxToTargetSearcher;
	private Searcher<Position> playerToBoxSearcher;
	private Distance distance;
	private ArrayList<Position> boxesList;
	private ArrayList<Position> targets;
	private ArrayList<Position> targetsAcquired;
	private static final int MAX_NUM_OF_SEARCH = 20;
	
	public SokobanPlannable(String fileName, Searcher<Position> boxToTargetSearcher, Searcher<Position> playerToBoxSearcher, Distance distance) {
		this.level = load(fileName);
		currentLevel = level;
		this.boxToTargetSearcher = boxToTargetSearcher;
		this.playerToBoxSearcher = playerToBoxSearcher;
		this.distance = distance;
		this.boxesMap = new HashMap<>();
		this.targets = level.getTargets();
		this.boxesList = level.getBoxes();
		this.targetsAcquired = new ArrayList<>();
	}
	
	@Override
	public Clause getGoal() {
		
		Clause goal = new Clause(null);
		targets.sort(new Comparator<Position>() {

			@Override
			public int compare(Position o1, Position o2) {
				if(distance.getDistance(level.getPlayerPosition(), o1) <
						distance.getDistance(level.getPlayerPosition(), o2)){
					return 1;
				}
				return 0;
			}
		});
		
		for(Position position : targets){
			if(!targetsAcquired.contains(position)){
				SokPredicate sp = new SokPredicate("boxAt", "?", position.getH() + "," + position.getW());
				goal.add(sp);
			}
		}
		
		return goal;
	}

	@Override
	public Clause getKnowledgebase() {
		Clause kb = new Clause(null);
		Position playerPosition = currentLevel.getPlayerPosition();
		SokPredicate sp = new SokPredicate("playerAt", "player", playerPosition.getH() + "," + playerPosition.getW());
		kb.add(sp);
		
		int boxesCount = 0;
		for(Position position : boxesList){
			sp = new SokPredicate("boxAt", Integer.toString(boxesCount), position.getH() + "," + position.getW());
			boxesMap.put(position, Integer.toString(boxesCount));
			kb.add(sp);
			boxesCount++;
		}
		
		for(Position position : targets){
				
			sp = new SokPredicate("clearAt", "?", position.getH() + "," + position.getW());
			if(!kb.contradicts(sp)){
				kb.add(sp); 
			}
		}
		return kb;
	}

	@Override
	public Set<PlannerAction> getsatisfyingActions(Predicate top) {
		return null;
	}

	@Override
	public PlannerAction getSatisfyingAction(Predicate top, Clause kb) {
			Solution solution = null;

			String value = top.getValue();
			int x = Character.getNumericValue(value.charAt(0));
			int y = Character.getNumericValue(value.charAt(value.length()-1));
			Position target = new Position(x,y);
			
			Position boxPosition=null;
			Solution boxToTargetSolution=null;
			Solution playerToBoxSolution=null;
			
			int boxToTargetCounter = 0;
			int playerToBoxCounter = 0;

			int counter=0;
			
			while(solution == null && counter < MAX_NUM_OF_SEARCH){
				
				counter++; 
				Position position = getClosestBox(target);
				setCurrentLevel(kb, getStaticBoard());
				SokobanBoxToTargetAdapter searchable = new SokobanBoxToTargetAdapter(this.currentLevel);
				searchable.setInitialState(position);
				searchable.setGoalState(target);
			
				while(boxToTargetSolution == null && boxToTargetCounter < MAX_NUM_OF_SEARCH){
					boxToTargetSolution = boxToTargetSearcher.search(searchable);
					boxToTargetCounter++;
				}
				if(boxToTargetSolution != null){
					Action lastAction = boxToTargetSolution.getActions().get(boxToTargetSolution.getActions().size()-1);
					SokobanSearchable sa = new SokobanSearchable(this.currentLevel);
					sa.setInitialState(currentLevel.getPlayerPosition());
					sa.setGoalState(getGoalPosition(lastAction, position));
							
					while(playerToBoxSolution == null && playerToBoxCounter < MAX_NUM_OF_SEARCH){
						playerToBoxSolution = playerToBoxSearcher.search(sa);
						playerToBoxCounter++;
					}
					if(boxToTargetSolution != null && playerToBoxSolution != null){
						solution = new Solution();
						solution.add(playerToBoxSolution.getActions());
						solution.add(boxToTargetSolution.getActions());
						boxPosition = position;
						//System.out.println(solution.toString());
						
					}
				}
			}
		
		//move action
		String boxId = boxesMap.get(boxPosition);
		MoveAction action = new MoveAction("Move", boxId, target.getH() + "," + target.getW(), solution.getActions());
		Clause preConditions = new Clause(new Predicate("clearAt", "?", target.getH() + "," + target.getW()));
		Clause effects = new Clause(null);
		Clause deleteEffects = new Clause(null);
		
		action.setPreconditions(preConditions);
		
		effects.add(new Predicate("boxAt", boxId, target.getH() + "," + target.getW()));
		Position playerPosition = getGoalPosition(solution.getActions().get(solution.getActions().size()-1), target);
		effects.add(new Predicate("playerAt", "player", playerPosition.getH() + "," + playerPosition.getW()));

		action.setEffects(effects);
		
		deleteEffects.add(new Predicate("clearAt", "?", target.getH() + "," + target.getW()));
		
		action.setDeleteEffects(deleteEffects);

		boxesMap.remove(boxPosition);
		boxesList.remove(boxPosition);
		targetsAcquired.add(target);
		currentLevel.setPlayerPosition(playerPosition);
		
		return action;
	}

	private Position getClosestBox(Position target) {

		boxesList.sort(new Comparator<Position>() {

			@Override
			public int compare(Position o1, Position o2) {
				if(distance.getDistance(o1, target) >
						distance.getDistance(o2, target)){
					return 1;
				}
				return 0;
			}
		});
		return boxesList.get(boxesList.size()-1);
	}

	private Position getGoalPosition(Action action, Position position){
		
		try {
			Direction d = Level.getDirection(action.getName().split(" ")[1]);
			Position goalPosition = Level.nextPosition(position, Level.oppositeDirection(d));
			return goalPosition;
		} catch (LevelPolicyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Level load(String fileName){
		
		Level level = null;
		try {
			LevelLoader loader = LevelLoaders.choose(fileName);
			level = loader.load(new FileInputStream(fileName));
			level.setLevelName(fileName);
			return level;
		} catch (LevelLoaderException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setCurrentLevel(Clause kb, Cell[][] staticState){
		
		Set<Predicate> currentKb = kb.getPredicates();
		//Cell[][] currentState = new Cell[staticState.length][staticState[0].length]; //29.6
		Cell[][] currentState = new Cell[staticState.length][];
		for(int i=1; i<staticState.length; i++){
			currentState[i] = new Cell[staticState[i].length];
		}
		currentState = staticState; //check
		
		currentLevel = new Level(currentState);
		
		for(Predicate predicate : currentKb){
			switch (predicate.getType()) {
			case "boxAt":
				if(predicate.getId() != "?"){
					currentLevel.setCell(parsePosition(predicate.getValue()), new Box()); //check if working
				}	
				break;
			case "playerAt":
				currentLevel.setPlayerPosition(parsePosition(predicate.getValue()));
				break;
				
			default:
				break;
			}
		}
	}
	//fix initialization
	private Cell[][] getStaticBoard(){
		
		Cell[][] staticState = new Cell[level.getCells().length][];//[level.getCells()[1].length]; //check if init is good
		
		for(int i=1; i<level.getCells().length; i++){
			staticState[i] = new Cell[level.getCells()[i].length];
			for(int j=1; j<level.getCells()[i].length; j++){
				Cell currCell = level.getCells()[i][j];
				if (currCell instanceof Floor ||  currCell instanceof Target || currCell instanceof Wall){
					staticState[i][j] = currCell;
				}
				else{
					staticState[i][j] = new Floor();
				}
			}
		}
		return staticState;
	}
	
	public static Position parsePosition(String s){
		return new Position(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1]));
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value){
		for(Entry<T, E> entry : map.entrySet()){
			if(Objects.equals(value, entry.getValue())){
				return entry.getKey();
			}
		}
		return null;
		
	}
}
