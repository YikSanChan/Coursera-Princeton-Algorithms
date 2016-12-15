package w1_UnionFind;

import java.util.Arrays;

/**
 * Created by CYS on 2016/12/14.
 */
public class QuickUnionUF {

    private int[] id;
    private int[] sz;
    private int count;

    public QuickUnionUF(int N){
        id = new int[N];
        sz = new int[N];
        count = N;
        for (int i = 0; i < N; i++) id[i] = i;
        Arrays.fill(sz, 1);
    }

    public int count(){
        return count;
    }

    private int root(int i){
        int root = i;
        while (root != id[root]) root = id[root];
        while (i != root) {
            int parent = id[i];
            id[i] = root;
            i = parent;
        }
        return root;
    }

    public boolean connected(int p, int q){
        return  root(p) == root(q);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) {id[i] = j; sz[j] += sz[i];}
        else               {id[j] = i; sz[i] += sz[j];}
        count--;
    }

}
