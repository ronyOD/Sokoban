package model.data.loaders;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import model.data.levels.Level;

public class MyObjectLevelLoader implements LevelLoader {

	@Override
	public Level load(InputStream inputStream) throws LevelLoaderException {

		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			return (Level) objectInputStream.readObject();
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}
	}

	@Override
	public void save(OutputStream outputStream, Level level) throws LevelLoaderException {

		try {
			if (level == null)
				return; // no level to save

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(level);
			objectOutputStream.flush();
		} catch (Exception e) {
			throw new LevelLoaderException(e);
		}
	}

}
