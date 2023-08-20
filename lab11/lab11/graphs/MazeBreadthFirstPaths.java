package lab11.graphs;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    // One dimensional coordinate of the source vertex
    private int s;
    // One dimensional coordinate of the target vertex
    private int t;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(s);
        marked[s] = true;
        announce();
        while (!queue.isEmpty()) {
            int v = queue.poll();
            if (v == t) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    queue.add(w);
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    edgeTo[w] = v;
                    announce();
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs();
    }
}

