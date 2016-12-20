package PA4;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by CYS on 2016/12/19.
 */
public class TestBoard {

    @Test
    public void testConstructor(){
        int[][] tiles = {{4,8,2},{1,0,5},{6,3,7}};
        Board board = new Board(tiles);
        assertEquals(board.dimension(), 3);
    }

    @Test
    public void testHamming(){
        int[][] tiles = {{8,1,3},{4,0,2},{7,6,5}};
        Board board = new Board(tiles);
        assertEquals(board.hamming(), 5);
    }

    @Test
    public void testManhattan(){
        int[][] tiles = {{8,1,3},{4,0,2},{7,6,5}};
        Board board = new Board(tiles);
        assertEquals(board.manhattan(), 10);
    }

    @Test
    public void testIsGoal(){
        int[][] tiles = {{8,1,3},{4,0,2},{7,6,5}};
        Board board = new Board(tiles);
        assertEquals(board.isGoal(), false);
        tiles = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
        board = new Board(tiles);
        assertEquals(board.isGoal(), true);
    }

    @Test
    public void testTwin(){
        int[][] tiles = {{1,2,3},{4,5,6},{7,8,0}};
        Board board = new Board(tiles);
        assertEquals(board.isGoal(), true);
        board = board.twin();
        assertEquals(board.isGoal(), false);
        System.out.println(board);
    }

    @Test
    public void testEquals(){
        int[][] tiles1 = {{8,1,3},{4,0,2},{7,6,5}};
        Board board1 = new Board(tiles1);
        int[][] tiles2 = {{8,1,3},{4,0,2},{7,6,5}};
        Board board2 = new Board(tiles2);
        assertEquals(board1.equals(board2), true);
        tiles2[0][0] = 9;
        board2 = new Board(tiles2);
        assertEquals(board1.equals(board2), false);
    }

    @Test
    public void testNeighbors(){
        int[][] tiles = {{0,1,3},{4,2,5},{7,8,6}};
        Board board = new Board(tiles);
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
    }
}
