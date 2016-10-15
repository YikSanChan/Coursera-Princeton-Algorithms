package w3_QuickSort;

import edu.princeton.cs.algs4.StdRandom;

import static w3_MergeSort.Merge.less;

/**
 * Created by CYS on 2016/10/14.
 */
public class Quick {

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static int partition(Comparable[] a, int lo, int hi){
        int i = lo, j = hi + 1;
        while (true){
            // find item on left to swap
            while (less(a[++i], a[lo]))
                if (i == hi) break;
            // find item on right to swap
            while (less(a[lo], a[--j]))
                if (j == lo) break;
            // check if pointers cross
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j; // index of item now known to be in place
    }

    public static void sort(Comparable[] a){
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi){
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    public static void main(String[] args){
        Integer[] a = new Integer[]{4,9,0,1,-9,7,5};
        Quick.sort(a);
        for (Integer i: a) System.out.print(i + " ");
        System.out.println();
    }
}
