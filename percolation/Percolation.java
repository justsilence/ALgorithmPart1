/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;

    private int nums;

    private int top;

    private int bottom;

    private int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private WeightedQuickUnionUF weightedQuickUnionUF;

    private WeightedQuickUnionUF weightedQuickUnionUFIsFull;

    private int countOpenSites = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        weightedQuickUnionUFIsFull = new WeightedQuickUnionUF(n * n + 1);
        nums = n;
        top = n * n;
        bottom = top + 1;
        grid = new boolean[n + 1][n + 1];
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException();

        if (!grid[row][col]) {
            if (row == 1) {
                int curr = xyTo1D(row, col);
                weightedQuickUnionUF.union(curr, top);
                weightedQuickUnionUFIsFull.union(curr, top);
            }

            if (row == nums) {
                int curr = xyTo1D(row, col);
                weightedQuickUnionUF.union(curr, bottom);
            }

            grid[row][col] = true;
            countOpenSites++;
            int curr = xyTo1D(row, col);
            for (int[] dir : directions) {
                int x = row + dir[0];
                int y = col + dir[1];
                if (validate(x, y) && isOpen(x, y)) {
                    int pos = xyTo1D(x, y);
                    weightedQuickUnionUF.union(pos, curr);
                    weightedQuickUnionUFIsFull.union(pos, curr);
                }
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException();
        return grid[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validate(row, col))
            throw new IllegalArgumentException();
        if (!isOpen(row, col))
            return false;

        int curr = xyTo1D(row, col);
        return weightedQuickUnionUFIsFull.connected(curr, top);

    }

    // number of open sites
    public int numberOfOpenSites() {
        return countOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(bottom, top);
    }


    // given x, y -> 1-D
    private int xyTo1D(int row, int col) {
        return (row - 1) * nums + (col - 1);
    }

    private boolean validate(int row, int col) {
        return (row >= 1 && row <= nums && col >= 1 && col <= nums);
    }


    // test client (optional)
    public static void main(String[] args) {
        In in = new In("input1-no.txt");      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);

        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }

        if (perc.percolates())
            System.out.println("Yes");
        else
            System.out.println("No");

    }
}
