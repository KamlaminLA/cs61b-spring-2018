package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int[][] tiles;
    private int size;
    private final int BLANK = 0;
    /** Constructs a board from an N-by-N array of tiles where
     *  tiles[i][j] = tile at row i, column j
     * @param tiles
     */
    public Board(int[][] tiles) {
        if (tiles == null || tiles[0].length != tiles.length) {
            throw new IllegalArgumentException("tiles can't be null and has be be N * N");
        }
        size = tiles[0].length;
        int N = tiles[0].length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        int N = tiles[0].length;
        if (i >= N || i < 0 || j >= N || j < 0) {
            throw new IndexOutOfBoundsException("i must between 0 and N - 1");
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Returns the neighbors of the current board
     * @return all neighbors
     * @source default solution
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * Hamming estimate described below
     * @return
     */
    public int hamming() {
        int N = tiles[0].length;
        int res = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (tileAt(i, j) == 0) {
                    continue;
                } else if (tileAt(i, j) != N * i + j + 1) {
                    res += 1;
                }
            }
        }
        return res;
    }

    /**
     * Manhattan estimate described below
     * @return
     */
    public int manhattan() {
        int N = tiles[0].length;
        int res = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (tileAt(i, j) == 0) {
                    continue;
                } else if (tileAt(i, j) != i * N + j + 1) {
                    int actualI = (tiles[i][j] - 1) / N;
                    int actualJ = (tiles[i][j] - 1) % N;
                    res += Math.abs(actualI + actualJ - i - j);
                }
            }
        }
        return res;
    }

    /**
     * Estimated distance to goal. This method should simply return the results of
     * manhattan() when submitted to Gradescope.
     * @return
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     * @param y
     * @return
     */
    @Override
    public boolean equals(Object y) {
        int N = tiles[0].length;
        if (y == this) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }
        Board other = (Board) y;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (this.tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
