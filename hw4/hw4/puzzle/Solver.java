package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.List;
import java.util.ArrayList;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState state;
        private int moves;
        private SearchNode prev;

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
        }

        public WorldState getState() {
            return state;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrev() {
            return prev;
        }
        @Override
        // minPQ will help us sort the SearchNode according to their
        // move distance and ESTG, lowest value will put to the front
        public int compareTo(SearchNode o) {
            int currentState = this.moves + this.state.estimatedDistanceToGoal();
            int objState = o.moves + o.state.estimatedDistanceToGoal();
            return currentState - objState;
        }
    }
    private MinPQ<SearchNode> pq;
    private int totalMoves;
    private List<WorldState> list;

    /** after we solve the puzzle, updates the property for later use */
    private void updates(SearchNode node) {
        totalMoves = node.getMoves();
        list = new ArrayList<>();
        while (node != null) {
            list.add((node.state));
            node = node.prev;
        }
    }
    public Solver(WorldState initial) {
        pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        while (true) {
            SearchNode curr = pq.delMin();
            if (curr.state.isGoal()) {
                updates(curr);
                return;
            } else {
                for (WorldState neighbor : curr.state.neighbors()) {
                    if (curr.prev == null || !neighbor.equals(curr.prev.state)) {
                        pq.insert(new SearchNode(neighbor, curr.moves + 1, curr));
                    }
                }
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return totalMoves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     * @return Iterable<WorldState>
     */
    public Iterable<WorldState> solution() {
        List<WorldState> ret = new ArrayList<>();
        for (int i = totalMoves; i >= 0; i--) {
            ret.add(list.get(i));
        }
        return ret;
    }
}
