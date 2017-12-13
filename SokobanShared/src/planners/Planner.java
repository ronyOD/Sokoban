package planners;

import java.util.List;

import plannable.PlannerAction;
import plannable.Plannable;

public interface Planner {

	List<PlannerAction> plan(Plannable plannable);
}
