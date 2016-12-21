package w2_ElementarySorts;

import java.util.Random;

/**
 * Created by CYS on 2016/12/15.
 */
public class StdRandom {

    public static void shuffle(Object[] a){
        int N = a.length;
        for (int i = 0; i < N; i++){
            int r = new Random().nextInt(i + 1);
            exch(a, i, r);
        }
    }

    private static void exch(Object[] a, int i, int j){
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
}
