package model.data.loaders;

import java.io.InputStream;
import java.io.OutputStream;

import model.data.levels.Level;

/**
 * a. Loader produces (load) or consumes (save) Level object. 
 *    It only knows the Level's internals by contract: access methods and constructor. 
 * b. Level's implementation details are hidden from Loader, so adding more loaders won't change Level.
 * c. Level's implementation details are hidden from Loader, so changing Level's internals won't change Loaders.
 * d. It follows SOLID. Passing stream allows reading/writing by conract, while sparing concrete Loader(s) from knowing anything
 *    about how Streams are created and handled. For example: how to open a file, read it, write it, close it, and many more.
 */
public interface LevelLoader {

	public Level load(InputStream level) throws LevelLoaderException;

	public void save(OutputStream outputStream, Level level) throws LevelLoaderException;

}
