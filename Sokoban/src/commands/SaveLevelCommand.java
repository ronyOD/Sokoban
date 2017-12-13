package commands;

import java.io.FileOutputStream;

import model.data.loaders.LevelLoader;

public class SaveLevelCommand extends LevelCommand {

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
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			levelLoader.save(fileOutputStream, level);
		} catch (Exception e) {
			throw new LevelCommandException(e);
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (Exception e) {
				throw new LevelCommandException(e);
			}
		}
		// System.out.println("Save: " + file);
	}

}
