package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites;
    private int N;
    // created a WeightedQuickUnionUF as instance variable
    private WeightedQuickUnionUF percolationWQU;
    private WeightedQuickUnionUF percolationWQU2;
    private int topSentinel;      // topSentinel connect to the top N items;
    private int lowSentinel;      // lowSentinel connect to the low N items;
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must >= 0");
        }
        this.N = N;
        percolationWQU = new WeightedQuickUnionUF(N * N + 2);
        percolationWQU2 = new WeightedQuickUnionUF(N * N + 1);
        topSentinel = N * N;
        lowSentinel = N * N + 1;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                grid[i][j] = false;
            }
        }
        // connect top N items with topSentinel
        for (int i = 0; i < N; i += 1) {
            percolationWQU.union(topSentinel, i);
            percolationWQU2.union(topSentinel, i);
        }
        // connect the low N items with lowSentinel
        for (int i = N * (N - 1); i < N * N; i += 1) {
            percolationWQU.union(lowSentinel, i);
        }
        openSites = 0;

    }
    // get the current site(row, col) id
    private int getID(int row, int col) {
        return row * N + col;
    }
    // open the site(row, col) if not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites += 1;
            if (checkAdjacent(row - 1, col)) {
                percolationWQU.union(getID(row, col), getID(row - 1, col));
                percolationWQU2.union(getID(row, col), getID(row - 1, col));
            }
            if (checkAdjacent(row + 1, col)) {
                percolationWQU.union(getID(row, col), getID(row + 1, col));
                percolationWQU2.union(getID(row, col), getID(row + 1, col));
            }
            if (checkAdjacent(row, col + 1)) {
                percolationWQU.union(getID(row, col), getID(row, col + 1));
                percolationWQU2.union(getID(row, col), getID(row, col + 1));
            }
            if (checkAdjacent(row, col - 1)) {
                percolationWQU.union(getID(row, col), getID(row, col - 1));
                percolationWQU2.union(getID(row, col), getID(row, col - 1));
            }
        }

    }
    // check adjacent site is open?
    private boolean checkAdjacent(int row, int col) {
        try {
            boolean x = isOpen(row, col);
            if (x) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
    // check whether the (row, col) is valid
    private boolean validate(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            return false;
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        if (percolationWQU.connected(topSentinel, getID(row, col))) {
            if (percolationWQU2.connected(topSentinel, getID(row, col))) {
                return true;
            }
        }
        return false;
    }

    // return the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolates?
    public boolean percolates() {
        return percolationWQU.connected(topSentinel, lowSentinel);
    }

    // use for unit testing
    public static void main(String[] args) {

    }
}
