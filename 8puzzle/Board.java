/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {
    // 0 means this position is empty.
    private static final int[][] GOAL = {{1,2,3}, {4,5,6}, {7,8,0}};

    private int[][] board;

    private int dimension;

    public Board(int[][] blocks) {
        dimension = blocks.length;
        board = copyBlocks(blocks);
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != 0 && board[i][j] != GOAL[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] != 0 && board[i][j] != GOAL[i][j]) {
                    int x = (board[i][j] - 1) / dimension;
                    int y = (board[i][j] - 1) % dimension;
                    result += (Math.abs(x - i) + Math.abs(y - j));
                }
            }
        }
        return result;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] copiedBlocks = copyBlocks(board);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (j + 1 < dimension && board[i][j] != 0 && board[i][j + 1] != 0) {
                    int tmp = copiedBlocks[i][j];
                    copiedBlocks[i][j] = copiedBlocks[i][j + 1];
                    copiedBlocks[i][j + 1] = tmp;
                    return new Board(copiedBlocks);
                }
            }
        }
        return null;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board b = (Board) y;
        if (this.dimension() != b.dimension()) return false;
        return Arrays.deepEquals(this.board, b.board);
    }

    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        int x = 0;
        int y = 0;

        int[][] copiedBlocks = copyBlocks(board);
        int[] pos = getBlankPosition(copiedBlocks);
        if (pos != null) {
            x = pos[0];
            y = pos[1];
        }
        if (x - 1 >= 0) {
            copiedBlocks[x][y] = copiedBlocks[x - 1][y];
            copiedBlocks[x - 1][y] = 0;
            queue.enqueue(new Board(copiedBlocks));
            copiedBlocks[x - 1][y] = copiedBlocks[x][y];
            copiedBlocks[x][y] = 0;
        }

        if (x + 1 < dimension) {
            copiedBlocks[x][y] = copiedBlocks[x + 1][y];
            copiedBlocks[x + 1][y] = 0;
            queue.enqueue(new Board(copiedBlocks));
            copiedBlocks[x + 1][y] = copiedBlocks[x][y];
            copiedBlocks[x][y] = 0;
        }
        if (y - 1 >= 0) {
            copiedBlocks[x][y] = copiedBlocks[x][y - 1];
            copiedBlocks[x][y - 1] = 0;
            queue.enqueue(new Board(copiedBlocks));
            copiedBlocks[x][y - 1] = copiedBlocks[x][y];
            copiedBlocks[x][y] = 0;
        }
        if (y + 1 < dimension) {
            copiedBlocks[x][y] = copiedBlocks[x][y + 1];
            copiedBlocks[x][y + 1] = 0;
            queue.enqueue(new Board(copiedBlocks));
            copiedBlocks[x][y + 1] = copiedBlocks[x][y];
            copiedBlocks[x][y] = 0;
        }
        return queue;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int[][] copyBlocks(int[][] blocks) {
        int[][] copiedBlocks = new int[dimension][dimension];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                copiedBlocks[i][j] = blocks[i][j];
            }
        }
        return copiedBlocks;
    }

    private int[] getBlankPosition(int[][] blocks) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < dimension; i ++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
