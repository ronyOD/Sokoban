package plannable;

public class PlannerAction extends Predicate{

	
	/*
	 * Move (fromX, fromY, toX, toY)
	 * Push {up,down,left,right}
	 * */
	
	private Clause preconditions,effects,deleteEffects;
	
	public PlannerAction(String type, String id, String value) {
		super(type, id, value);
	}	

	public Clause getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(Clause preconditions) {
		this.preconditions = preconditions;
	}

	public Clause getEffects() {
		return effects;
	}

	public void setEffects(Clause effects) {
		this.effects = effects;
	}

	public Clause getDeleteEffects() {
		return deleteEffects;
	}

	public void setDeleteEffects(Clause deleteEffects) {
		this.deleteEffects = deleteEffects;
	}
	
}
