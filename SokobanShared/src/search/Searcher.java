package search;

public interface Searcher<T> {

	Solution search(Searchable<T> s);
	int getNumberOfNodesEvaluated();
}
