package PA2;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by CYS on 2016/9/3.
 */
public class Subset {
    public static void main(String[] args){

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()){
            String tmp = StdIn.readString();
            rq.enqueue(tmp);
        }
        int k = Integer.parseInt(args[0]);
        while (k-- > 0){
            System.out.println(rq.dequeue());
        }
    }
}
