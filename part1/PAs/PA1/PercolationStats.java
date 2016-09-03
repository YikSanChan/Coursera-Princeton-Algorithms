package PA1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation[] pList;
    private double[] percFrac;// percolation fraction
    private int T;

    public PercolationStats(int N, int t){
        T = t;
        if (N < 1 || T < 1){
            throw new IllegalArgumentException();
        } else if (N == 1){ // special case
            percFrac = new double[T];
            for (int i = 0; i < percFrac.length; i++){
                percFrac[i] = 1.0;
            }
        } else {
            percFrac = new double[T];
            pList = new Percolation[T]; // why initialization fails?
            for (int i = 0; i < T; i++){
                pList[i] = new Percolation(N);
                int cnt = 0;
                while (!pList[i].percolates()){
                    int x = StdRandom.uniform(N) + 1;
                    int y = StdRandom.uniform(N) + 1;
                    if (!pList[i].isOpen(x, y)){
                        pList[i].open(x, y);
                        cnt++;
                    }
                }
                percFrac[i] = (double)cnt / (double)(N * N);
            }
        }
    }

    public double mean(){
        return StdStats.mean(percFrac);
    }
    public double stddev(){
        return StdStats.stddev(percFrac);
    }
    public double confidenceLo(){
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    public double confidenceHi(){
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args){

        int N = Integer.parseInt(args[0]);//200
        int T = Integer.parseInt(args[1]);//100
        PercolationStats ps = new PercolationStats(N, T);
        System.out.printf("mean\t= %f\n", ps.mean());
        System.out.printf("stddev\t= %f\n", ps.stddev());
        System.out.printf("95%% confidence interval\t= %f, %f\n", ps.confidenceLo(), ps.confidenceHi());
    }

}                       
