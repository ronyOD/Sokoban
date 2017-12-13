package commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import model.data.loaders.LevelLoader;

public class LoadLevelCommand extends LevelCommand {

	private LevelLoader levelLoader;

	public void setLevelLoader(LevelLoader levelLoader) {
		this.levelLoader = levelLoader;
	}

	private String file;

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public void execute() throws LevelCommandException {
		if (levelLoader != null) {
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
				level = levelLoader.load(fileInputStream);
				//set file name
				level.setLevelName(file.split("\\.")[0]);
			} catch (FileNotFoundException e) {
				throw new LevelCommandException(e.getMessage());
			} catch (Exception e) {
				throw new LevelCommandException(e);
			} finally {
				try {
					if (fileInputStream != null)
						fileInputStream.close();
				} catch (IOException e) {
					throw new LevelCommandException(e);
				}
			}
			// System.out.println("Load: " + file);
		}
	}

}
