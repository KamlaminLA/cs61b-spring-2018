package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private boolean isFound = false;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        distTo[0] = 0;
        edgeTo[0] = 0;
        dfsHelper(maze, 0);
    }

    // Helper methods go here
    private void dfsHelper(Maze m, int v) {
        marked[v] = true;
        announce();
        for (int w : m.adj(v)) {
            if (edgeTo[w] != v && marked[w]) {
                announce();
                isFound = true;
            }
            if (isFound) {
                return;
            }
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] += 1;
                dfsHelper(m, w);
            }

        }
    }
}

