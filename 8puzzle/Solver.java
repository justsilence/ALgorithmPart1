/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int moves;

    private SearchNode resultNode;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode predecessor;
        private int moves;
        private int priority;

        public SearchNode(Board board, SearchNode p, int moves) {
            this.board = board;
            predecessor = p;
            this.moves = moves;
            priority = board.manhattan() + this.moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<>();

        minPQ.insert(new SearchNode(initial, null, 0));
        minPQTwin.insert(new SearchNode(initial.twin(), null, 0));

        while (true) {
            SearchNode curr = minPQ.delMin();
            SearchNode currTwin = minPQTwin.delMin();

            if (curr.board.isGoal()) {
                moves = curr.moves;
                resultNode = curr;
                break;
            } else if (currTwin.board.isGoal()) {
                moves = -1;
                resultNode = null;
                break;
            }

            for (Board b : curr.board.neighbors()) {
                if (curr.predecessor == null || !b.equals(curr.predecessor.board))
                    minPQ.insert(new SearchNode(b, curr, curr.moves + 1));
            }

            for (Board b : currTwin.board.neighbors()) {
                if (currTwin.predecessor == null || !b.equals(currTwin.predecessor.board))
                    minPQTwin.insert(new SearchNode(b, currTwin, currTwin.moves + 1));
            }
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> st = new Stack<>();
        SearchNode node = resultNode;
        while (node != null) {
            st.push(node.board);
            node = node.predecessor;
        }
        return st;

    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

    }
}
