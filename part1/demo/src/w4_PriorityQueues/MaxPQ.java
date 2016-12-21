package w4_PriorityQueues;

/**
 * Created by CYS on 2016/12/19.
 */
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;

    public MaxPQ(int capacaity){
        pq = (Key[]) new Comparable[capacaity + 1];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    private void swim(int k){
        while (k > 1 && less(k, k / 2)){
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k){
        while (2 * k <= N){
            int j = 2 * k;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    public void insert(Key x){
        pq[++N] = x;
        swim(N);
    }

    public Key delMax(){
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        return max;
    }

    private boolean less(int i, int j){
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j){
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
}
