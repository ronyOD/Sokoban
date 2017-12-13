package plannable;

import java.util.Set;

public interface Plannable {

	Clause getGoal();
	Clause getKnowledgebase();
	Set<PlannerAction> getsatisfyingActions(Predicate top);
	PlannerAction getSatisfyingAction(Predicate top, Clause kb);
	
}
