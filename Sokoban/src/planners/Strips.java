package planners;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import plannable.PlannerAction;
import plannable.Clause;
import plannable.Plannable;
import plannable.Predicate;

public class Strips extends CommonPlanner{

	private Plannable plannable;

	/*
	 * (non-Javadoc)
	 * @see strips.Planner#plan(strips.Plannable)
Push goal into the stack
Repeat until stack is empty
If top is a multipart goal, push unsatisfied sub-goals into the stack V
If top is a single unsatisfied goal, V
Replace it with an action that satisfies the goal V
Push the action preconditions into the stack V
If top is an action, V
Pop it from the stack V
Simulate its execution and update the knowledge base with its effects V
Add the action to the plan V
If top is a satisfied goal, pop it from the stack V
	 */
	public List<PlannerAction> plan(Plannable plannable) {
		LinkedList<PlannerAction> plan=new LinkedList<>();
		this.plannable=plannable;
		Stack<Predicate> stack=new Stack<>();
		Clause kb = plannable.getKnowledgebase();
		stack.push(plannable.getGoal());
		while(!stack.isEmpty()){
			Predicate top=stack.peek();
			if(! (top instanceof PlannerAction)){
				if(!kb.satisfies(top)){ // unsatisfied
					if(top instanceof Clause){ // multipart
						Clause c=(Clause)top; 
						for(Predicate p : c.getPredicates()){
							stack.push(p); //no logic in entering predicates to stack - if we wish to - strategy pattern
						}
					}else{ // single and unsatisfied
						stack.pop();
						PlannerAction action=plannable.getSatisfyingAction(top, kb); //
						stack.push(action);
						stack.push(action.getPreconditions());
					}
				}else{
					stack.pop();
				}
			}else{ // top is an action at the top of the stack
				stack.pop();
				PlannerAction a=(PlannerAction)top;
				kb.update(a.getEffects(), a.getDeleteEffects());
				plan.add(a);
			}
		}
		return plan;
	}

}
