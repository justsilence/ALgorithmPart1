/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform trials independent experiments on an n-by-n grid
    private double mean;
    private double stddev;
    private int T;

    private static final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        T = trials;
        double[] xt = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                percolation.open(x, y);
            }
            int total = percolation.numberOfOpenSites();
            xt[i] = (double) total / (n * n);
        }
        mean = StdStats.mean(xt);
        stddev = StdStats.stddev(xt);
    }


    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }


    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double ans = mean - CONFIDENCE_95 * stddev / Math.sqrt(T);
        return ans;
    }


    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double ans = mean + CONFIDENCE_95 * stddev / Math.sqrt(T);
        return ans;
    }


    // test client (described below)
    public static void main(String[] args) {
        // PercolationStats percolationStats = new PercolationStats(200, 100);
        // System.out.println("mean                    = " + percolationStats.mean());
        // System.out.println("stddev                  = " + percolationStats.stddev());
        // System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() +"]");
        String s = "a==b";
        String[] contetns = s.split("==");
        for (String str : contetns) {
            System.out.println(str);
        }

    }

}
