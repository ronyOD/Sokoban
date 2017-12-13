package plannable;

public class SokPredicate extends Predicate {

	public SokPredicate(String type, String id, String value) {
		super(type, id, value);
	}
	
/*	@Override
	public boolean contradicts(Predicate p) {
		return super.contradicts(p) || (!id.equals(p.id) && value.equals(p.value));
	}*/
	
	@Override
	public boolean contradicts(Predicate p) {
		return super.contradicts(p) || (!this.getId().equals(p.getId()) && this.getValue().equals(p.getValue()));
	}

}
