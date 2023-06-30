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
        if(N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T must greater than 0");
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        res = new double[T];
    }

    //sample mean of percolation threshold
    public double mean() {
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int x = StdRandom.uniform(N);
                if (!p.isOpen(x / N, x % N)) {
                    p.open(x / N, x % N);
                }
            }
            res[i] = p.numberOfOpenSites();
        }
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
        double low = meanFromSample - (1.96 * sd / Math.sqrt(T));
        return low;
    }

    //high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double high = meanFromSample + (1.96 * sd / Math.sqrt(T));
        return high;
    }
}
