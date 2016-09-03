package PA1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private boolean[] open;
    private WeightedQuickUnionUF percUF, fullUF; // UF used for percolates() and isFull()

    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException("argument of Percolation() isn't positive");
        N = n;
        open = new boolean[N * N]; // open[i] stores open status for (i+1)th site
        percUF = new WeightedQuickUnionUF(2 + N * N);
        fullUF = new WeightedQuickUnionUF(1 + N * N);
        // Connect top site to sites in the 1st row
        for (int i = xyTo1D(1, 1); i < xyTo1D(2, 1); i++){
            percUF.union(topIndex(), i);
            fullUF.union(topIndex(), i);
        }
        // To avoid backwash, do not connect bottom sites to the last row for fullUF, but only for percUF
        for (int i = xyTo1D(N, 1); i < xyTo1D(N + 1, 1); i++){
            percUF.union(bottomIndex(), i);
        }
    }

    private int topIndex(){
        return 0;
    }
    private int bottomIndex(){
        return 1 + N * N;
    }

    private int xyTo1D(int x, int y){ // xth row, yth col
        return (x - 1) * N + y;
    }

    private boolean valid(int row, int col){
        return (row >= 1) && (row <= N) && (col >= 1) && (col <= N);
    }

    /**
     * Check grid around, connect to those open
     */
    private void connectAround(int row, int col){
        int[] openIndexs = new int[4]; // index of places that are also open
        int cnt = 0;
        if (valid(row - 1, col) && isOpen(row - 1, col)){
            openIndexs[cnt] = xyTo1D(row - 1, col);
            cnt++;
        }
        if (valid(row + 1, col) && isOpen(row + 1, col)) {
            openIndexs[cnt] = xyTo1D(row + 1, col);
            cnt++;
        }
        if (valid(row, col - 1) && isOpen(row, col - 1)){
            openIndexs[cnt] = xyTo1D(row, col - 1);
            cnt++;
        }
        if (valid(row, col + 1) && isOpen(row, col + 1)){
            openIndexs[cnt] = xyTo1D(row, col + 1);
            cnt++;
        }
        while (cnt > 0){ // add connect around
            int toConnect = openIndexs[--cnt];
            percUF.union(xyTo1D(row, col), toConnect);
            fullUF.union(xyTo1D(row, col), toConnect);
        }
    }

    public void open(int row, int col){
        if (isOpen(row, col)) return; // important
        if (!valid(row, col)) throw new IndexOutOfBoundsException();
        open[xyTo1D(row, col) - 1] = true;
        connectAround(row, col);
    }

    public boolean isOpen(int row, int col){
        if (!valid(row, col)) throw new IndexOutOfBoundsException();
        return open[xyTo1D(row, col) - 1];
    }
    public boolean isFull(int row, int col){
        if (!valid(row, col)) throw new IndexOutOfBoundsException();
        // not allow the 1st row the show isFull before they are opened
        return isOpen(row, col) && fullUF.connected(topIndex(), xyTo1D(row, col));
    }
    public boolean percolates(){
        return percUF.connected(topIndex(), bottomIndex());
    }
}                       
