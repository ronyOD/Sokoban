package plannable;

import java.util.HashSet;
import java.util.Set;

public class Clause extends Predicate{

	HashSet<Predicate> predicates;
	
	private void updateDescription(){
		value="{";
		for(Predicate p : predicates){
			value+=p.toString()+" & ";
		}
		value+="}";
	}
	
	public Clause(Predicate...predicates) {
		super("And", "", "");
		if(predicates!=null){
			this.predicates=new HashSet<>();
			for(Predicate p : predicates){
				this.predicates.add(p);
			}
			updateDescription();
		}
	}

	public void update(Clause effect, Clause deleteEffects) {
		effect.predicates.forEach((Predicate p)->predicates.removeIf((Predicate pr)->p.contradicts(pr)));
		predicates.addAll(effect.predicates);
		updateDescription();
		
		for(Predicate predicate : deleteEffects.getPredicates()){
			for(Predicate p : predicates){
				if(predicate.equals(p)){
					predicates.remove(p);
					updateDescription();
					break;
				}
			}
		}
	}
	
	public void add(Predicate p){
		if(predicates==null)
			predicates=new HashSet<>();		
		this.predicates.add(p);
		updateDescription();
	}
	
	
	@Override
	public boolean satisfies(Predicate p){
		
		if(p instanceof Clause){
			Clause c = (Clause)p;
			for(Predicate predicate : c.predicates){
				if(!satisfies(predicate))
					return false;
			}
			return true;
		}

		for(Predicate predicate : predicates)
			if(predicate.satisfies(p))
				return true;
		return false;
	}
	
	public boolean satisfies(Clause clause){
		for(Predicate p : clause.predicates){
			if(!satisfies(p))
				return false;
		}
		return true;
	}

	public Set<Predicate> getPredicates() {
		return predicates;
	}

}
