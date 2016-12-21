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
            while (less(a[++i], a[lo])) // Why ++i? Because a[lo] has been chosen as the partition item
                if (i == hi) break;     // boundary check: redundant
            // find item on right to swap
            while (less(a[lo], a[--j])) // j starts as hi + 1
                if (j == lo) break;     // boundary check
            // check if pointers cross
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);// a[j] <= a[lp]
        return j; // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
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

    /**
     * Find the kth smallest element in an array
     * @param a the array
     * @param k the rank of the key
     * @return the key of rank k
     */
    public static Comparable select(Comparable[] a, int k){
        if (k < 0 || k >= a.length)
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo){
            int j = partition(a, lo, hi);
            if      (j > k) hi = j - 1;
            else if (j < k) lo = j + 1;
            else            return a[j];
        }
        return a[lo];
    }

    public static void main(String[] args){
        Integer[] a = new Integer[]{4,9,0,1,-9,7,5};
        Quick.sort(a);
        for (Integer i: a) System.out.print(i + " ");
        System.out.println();
    }
}
