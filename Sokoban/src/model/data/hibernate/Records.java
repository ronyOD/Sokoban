package model.data.hibernate;

/**
* <h1>Records</h1>
* This class defines a Record Object
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class Records {

	private int Id;
	private String UserName;
	private String LevelName;
	private int NumOfSteps;
	private double Time;
	
	public Records(){};
	public Records(String UserName, String LevelName, int NumOfSteps, double Time){
		this.UserName = UserName;
		this.LevelName = LevelName;
		this.NumOfSteps = NumOfSteps;
		this.Time = Time;
	}
	
	public int getId(){
		return this.Id;
	}
	public void setId(int Id){
		this.Id = Id;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	public String getLevelName() {
		return LevelName;
	}
	public void setLevelName(String levelName) {
		LevelName = levelName;
	}
	public int getNumOfSteps() {
		return NumOfSteps;
	}
	public void setNumOfSteps(int numOfSteps) {
		NumOfSteps = numOfSteps;
	}
	public double getTime() {
		return Time;
	}
	public void setTime(double time) {
		Time = time;
	}
	
}
