package model;
import java.util.Observable;

import model.data.levels.Level;
import model.policy.Policy;

/**
* <h1>MyModel</h1>
* Sokoban Model holds Level and Policy Objects.
* This class extends Observable
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/

public class MyModel extends Observable {

	private Level level;
	private Policy policy;
	private String userName;

	
	/**
	* This method returns the level that the model currently holds.
	*@return Level
	*/
	public Level getLevel() {
		return level;
	}

	/**
	* This method returns the Policy that the model currently holds.
	*@return Policy
	*/
	public Policy getPolicy() {
		return policy;
	}

	/**
	* This method sets the models Policy.
	* @param policy is an object that extends LevelPolicy.
	*/
	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	/**
	* This method sets the models Level.
	* @param level is an object that represents a level file.
	*/
	public void setLevel(Level level) {
		this.level = level;
		setChanged();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}