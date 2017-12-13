package search;

import java.util.LinkedList;

public class Solution {

	private LinkedList<Action> actions = new LinkedList<Action>();

	public Solution() {
	}
	
	public Solution(String solutionCsv) {
		String[] actions = solutionCsv.split(",");
		for(String action : actions){
			char direction = action.charAt(1);
			switch(direction){
			case 'R':
				this.actions.add(new Action("Move right"));
				break;
			case 'L':
				this.actions.add(new Action("Move left"));
				break;
			case 'U':
				this.actions.add(new Action("Move up"));
				break;
			case 'D':
				this.actions.add(new Action("Move down"));
			}
		}
	}
	
	public LinkedList<Action> getActions() {
		return actions;
	}

	public void setActions(LinkedList<Action> actions) {
		this.actions = actions;
	}
	
	public Action getLastAction(){
		return actions.getFirst();
	}
	
	public void add(LinkedList<Action> actions){
		this.actions.addAll(actions);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Action a : actions) {
			sb.append(a.getName()).append("\n");
		}
		
		return sb.toString();
	}
	public String toCsv(){
		StringBuilder sb = new StringBuilder();
		
		for(Action a : actions){
			if(a.getName().startsWith("Move")){
				sb.append("M" + a.getName().split(" ")[1].charAt(0));
				sb.append(",");
			}
			else if (a.getName().startsWith("Push")){
				sb.append("P" + a.getName().split(" ")[1].charAt(0));
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
