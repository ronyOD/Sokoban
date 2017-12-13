package model.data.loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LevelLoaders {

	static {
		// prepare level loaders
		levelLoaders = new HashMap<String, LevelLoader>();
		LevelLoaders.map("txt", new MyTextLevelLoader());
		LevelLoaders.map("obj", new MyObjectLevelLoader());
		LevelLoaders.map("xml", new MyXmlLevelLoader());
	}

	private static Map<String, LevelLoader> levelLoaders;

	public static void map(String ext, LevelLoader levelLoader) {
		levelLoaders.put(ext, levelLoader);
	}

	/**
	* This static method chooses the appropriate loader.
	* @param file.
	* @return LevelLoader.
	* @exception LevelLoaderException On input error.
	*/
	public static LevelLoader choose(String file) throws LevelLoaderException {
		if (file == null)
			throw new LevelLoaderException("no file");
		String ext = file.substring(file.lastIndexOf('.') + 1);
		LevelLoader levelLoader = levelLoaders.get(ext);
		if (levelLoader == null)
			throw new LevelLoaderException("not known file type");
		return levelLoader;
	}

	public static Set<String> supported() {
		return levelLoaders.keySet();
	}
}
