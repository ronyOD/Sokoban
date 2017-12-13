package model.data.loaders;

import java.io.InputStream;
import java.io.OutputStream;

import model.data.levels.Level;

/**
* <h1>LevelLoader</h1>
* This class defines the methods to load levels.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/

/**
 * a. Loader produces (load) or consumes (save) Level object. 
 *    It only knows the Level's internals by contract: access methods and constructor. 
 * b. Level's implementation details are hidden from Loader, so adding more loaders won't change Level.
 * c. Level's implementation details are hidden from Loader, so changing Level's internals won't change Loaders.
 * d. It follows SOLID. Passing stream allows reading/writing by conract, while sparing concrete Loader(s) from knowing anything
 *    about how Streams are created and handled. For example: how to open a file, read it, write it, close it, and many more.
 */
public interface LevelLoader {

	/**
	* This method loads a level from text/xml/object file.
	* @param level.
	* @return Level.
	* @exception LevelLoaderException On input error.
	*/	
	public Level load(InputStream level) throws LevelLoaderException;

	/**
	* This method saves a level to text/xml/object file.
	* @param outputStream.
	* @param level.
	* @exception LevelLoaderException On input error.
	*/	
	public void save(OutputStream outputStream, Level level) throws LevelLoaderException;

}
