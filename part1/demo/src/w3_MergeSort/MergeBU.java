package w3_MergeSort;

/**
 * Created by CYS on 2016/10/12.
 */
public class MergeBU {
    /**
     * 辅助
     */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    /**
     * 核心
     */
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        // copy a to aux
        for (int k = lo; k <= hi; k++){
            aux[k] = a[k];
        }

        // copy back from aux to a
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++){
            if (i > mid) a[k] = aux[j++]; // 1st subarray used up
            else if (j > hi) a[k] = aux[i++]; // 2nd subarray used up
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
        assert isSorted(a, lo, hi);
    }

    public static void sort(Comparable[] a){
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(a, aux, lo, lo + sz - 1, Math.min(N - 1, lo + sz + sz - 1));
    }

    /** 测试 */
    public static void main(String[] args){
        Integer[] a = new Integer[]{4,9,0,1,-9,7,5};
        MergeBU.sort(a);
        for (Integer i: a) System.out.print(i + " ");
        System.out.println();
    }

}