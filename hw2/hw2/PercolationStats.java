package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private int N;
    private int T;
    private PercolationFactory pf;
    private double[] res;
    private double meanFromSample;
    private double sd;
    // perform T times experiment in N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T must greater than 0");
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        res = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int x = StdRandom.uniform(N * N);
                if (!p.isOpen(x / N, x % N)) {
                    p.open(x / N, x % N);
                }
            }
            res[i] = p.numberOfOpenSites() / (double) (N * N);
        }
    }

    //sample mean of percolation threshold
    public double mean() {
        meanFromSample = StdStats.mean(res);
        return meanFromSample;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        sd = StdStats.stddev(res);
        return sd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double low = StdStats.mean(res) - (1.96 * StdStats.stddev(res) / Math.sqrt(T));
        return low;
    }

    //high endpoint of 95% confidence interval
    // we can't just use the instance variable(sd and meanFromSample), because we may
    // call the confidenceHigh() or low() method first, it will return 0, since
    // sd and meanFromSample are not define yet
    public double confidenceHigh() {
        double high = StdStats.mean(res) + (1.96 * StdStats.stddev(res) / Math.sqrt(T));
        return high;
    }
}
