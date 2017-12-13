package displayers;

import model.data.levels.Level;
import model.data.loaders.LevelLoaderException;
import model.data.loaders.MyTextLevelLoader;

public class MyPrintLevelDisplayer implements LevelDisplayer {

	@Override
	public void display(Level level) throws LevelDisplayerException {
		try {
			new MyTextLevelLoader().save(System.out, level);
		} catch (LevelLoaderException e) {
			throw new LevelDisplayerException(e);
		}
	}

	@Override
	public void displaySaveResultsDialog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayResults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolutionDialog(String solution) {
		// TODO Auto-generated method stub
		
	}
	
	
}
