package PA4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by CYS on 2016/12/19.
 */
public class Solver {

    private boolean isSolvable;
    private SearchNode solution;

    private class SearchNode {

        private Board board;
        private SearchNode previous;
        private int moves;

        public SearchNode(Board board, SearchNode previous, int moves){
            this.board = board;
            this.previous = previous;
            this.moves = moves;
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode>{

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return (o1.moves + o1.board.manhattan()) - (o2.moves + o2.board.manhattan());
        }
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        // Determine solvable or not, reference http://www.cs.princeton.edu/courses/archive/fall12/cos226/assignments/8puzzle.html
        // However, this assignment abandons me from accessing Board object's board member, so I cannot use the method introduced above
        // Use twin() API instead

        Comparator<SearchNode> BY_PRIORITY = new SearchNodeComparator();

        MinPQ<SearchNode> pq = new MinPQ<>(initial.dimension() * initial.dimension(), BY_PRIORITY);
        MinPQ<SearchNode> pqTwin = new MinPQ<>(initial.dimension() * initial.dimension(), BY_PRIORITY);

        SearchNode initialNode = new SearchNode(initial, null, 0);
        SearchNode initialTwinNode = new SearchNode(initial.twin(), null, 0);

        pq.insert(initialNode);
        pqTwin.insert(initialTwinNode);

        SearchNode current = null;
        SearchNode currentTwin = null;

        solution = initialNode;

        while (!((current = pq.delMin()).board.isGoal()) && !((currentTwin = pqTwin.delMin()).board.isGoal())){
            for (Board b: current.board.neighbors()){
                if (current.previous != null && b.equals(current.previous.board)) // critical optimization
                    continue;
                pq.insert(new SearchNode(b, current, current.moves + 1));
            }
            for (Board b: currentTwin.board.neighbors()){
                if (currentTwin.previous != null && b.equals(currentTwin.previous.board))
                    continue;
                pqTwin.insert(new SearchNode(b, currentTwin, currentTwin.moves + 1));
            }
        }
        if (currentTwin != null && currentTwin.board.isGoal()) {
            isSolvable = false;
            return;
        }
        isSolvable = true;
        solution = current;
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return isSolvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return isSolvable ? solution.moves : -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<>();
        SearchNode tmp = solution; // immutability
        while (tmp != null){
            stack.push(tmp.board);
            tmp = tmp.previous;
        }
        return stack;
    }

    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
