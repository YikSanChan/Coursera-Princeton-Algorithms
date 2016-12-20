package PA4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by CYS on 2016/12/19.
 */
public class Board {

    private int[] board;
    private int dimension;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {
        dimension = blocks.length;
        int len = dimension * dimension;
        board = new int[len];
        for (int i = 0; i < dimension; i++){
            System.arraycopy(blocks[i], 0, board, i * dimension, dimension);
        }
    }
    private Board(int[] tiles){
        int len = tiles.length;
        dimension = (int) Math.sqrt(len);
        board = tiles;
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    {
        return dimension;
    }
    public int hamming()                   // number of blocks out of place
    {
        int outOfPlace = 0;
        for (int i = 0; i < dimension * dimension - 1; i++)
            if (board[i] != i + 1)
                outOfPlace++;
        return outOfPlace;
    }
    private int getX2D(int x1d)
    {
        return x1d / dimension;
    }
    private int getY2D(int x1d)
    {
        return x1d % dimension;
    }
    private int getX1D(int x2d, int y2d)
    {
        return x2d * dimension + y2d;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int sum = 0;
        for (int i = 0; i < dimension * dimension; i++){
            if (board[i] == 0) continue;
            int thisX = getX2D(i);
            int thisY = getY2D(i);
            int that = board[i] - 1;
            int thatX = getX2D(that);
            int thatY = getY2D(that);
            sum += Math.abs(thisX - thatX) + Math.abs(thisY - thatY);
        }
        return sum;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return manhattan() == 0;
    }
    private int indexOfBlank(){
        int res = -1;
        int len = dimension * dimension;
        for (int i = 0; i < len; i++)
            if (board[i] == 0) {
                res = i;
                break;
            }
        return res;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int indexOfBlank = indexOfBlank();
        int hasBlankRow = getX2D(indexOfBlank);
        int row = (hasBlankRow == 0 ? 1 : 0);
        int[][] blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++)
            System.arraycopy(board, i * dimension, blocks[i], 0, dimension);
        int tmp = blocks[row][0];
        blocks[row][0] = blocks[row][1];
        blocks[row][1] = tmp;
        return new Board(blocks);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == null) return false;
        if (this == y) return true;
        if (y.getClass() != this.getClass()) return false;
        return Arrays.equals(board, ((Board)y).board);
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> queue = new LinkedList<>();
        int indexOfBlank = indexOfBlank();
        int[] copy = Arrays.copyOf(board, dimension * dimension);

        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        int x = getX2D(indexOfBlank);
        int y = getY2D(indexOfBlank);
        for (int i = 0; i < 4; i++){
            if (x + dx[i] >= 0 && x + dx[i] < dimension && y + dy[i] >= 0 && y + dy[i] < dimension){
                int thatIndex = getX1D(x + dx[i], y + dy[i]);
                board[indexOfBlank] = board[thatIndex];
                board[thatIndex] = 0;
                Board newBoard = new Board(board);
                if (!queue.offer(newBoard))
                    throw new NullPointerException("Queue is full.");
                board = Arrays.copyOf(copy, copy.length);
            }
        }
        return queue;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension + "\n");
        for (int i = 0; i < dimension * dimension; i += dimension){
            for (int j = i; j < i + dimension; j++)
                sb.append(String.format("%2d ", board[j]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
