package search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import model.data.levels.Level;
import model.data.loaders.LevelLoader;
import model.data.loaders.LevelLoaderException;
import model.data.loaders.MyTextLevelLoader;
import model.data.positions.Position;

public class TestUnit {

	public static void main(String[] args) {
		
		Searcher<Position> searcher = new BFS<Position>();
		LevelLoader loader = new MyTextLevelLoader();
		Level level = null;
		try {
			level = loader.load(new FileInputStream(new File("level1.txt")));
			
			SokobanBoxToTargetAdapter sa = new SokobanBoxToTargetAdapter(level);
			Position boxPos = level.getBoxes().get(0);
			Position targetPos = level.getTargets().get(0);
			
			sa.setInitialState(boxPos);
			sa.setGoalState(targetPos); //give target position
			
			Solution s = searcher.search(sa);
			
			System.out.println(s.toString());
			
		} catch (FileNotFoundException | LevelLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
