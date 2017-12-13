package planners;

import java.util.List;

import plannable.Clause;
import plannable.Predicate;

public interface HeuristicMethods {
	List<Predicate> decomposeGoal(Clause g);
}
