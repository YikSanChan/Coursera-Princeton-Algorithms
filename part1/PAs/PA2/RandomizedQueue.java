package PA2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by CYS on 2016/9/3.
 * Use array to construct randomized queue.
 * Core part lies in dequeue() and iterator.next()
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 2;
    private static final int EXPAND_FACTOR = 2;
    private static final double SHRINK_FACTOR = 0.25;

    private int last; // keep track of last position to add in (not for remove)
    private Item[] items; // restore elements of queue
    private int size, capacity;

    /** empty randomized queue */
    public RandomizedQueue(){
        items = (Item[]) new Object[INIT_CAPACITY];
        capacity = INIT_CAPACITY;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private boolean isFull(){
        return size == capacity;
    }

    private boolean isSpacious(){
        return size < capacity * SHRINK_FACTOR;
    }

    public int size(){
        return size;
    }

    private void resize(int newCapacity){
        Item[] tmp = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++){
            tmp[i] = items[i];
        }
        items = tmp;
        capacity = newCapacity;
    }

    public void enqueue(Item item){
        if (item == null) throw new NullPointerException();
        if (isFull()) resize(capacity * EXPAND_FACTOR);
        items[last++] = item;
        size++;
    }

    public Item dequeue(){
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(size);
        Item tmp = items[r];
        items[r] = items[size - 1];
        items[size - 1] = null;
        last--; size--;
        if (isSpacious()) resize((int)(capacity * SHRINK_FACTOR));
        return tmp;
    }

    public Item sample(){
        if (isEmpty()) throw new NoSuchElementException();
        int r = StdRandom.uniform(size);
        return items[r];
    }

    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>{

        private Item[] compactCopy;
        private int cnt; // counter

        public RandomizedQueueIterator(){
            if (!isEmpty()){
                compactCopy = Arrays.copyOfRange(items, 0, size);
                StdRandom.shuffle(compactCopy);
            }
        }

        public boolean hasNext(){
            return cnt < size;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }

        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            return compactCopy[cnt++];
        }
    }

    /*
    public void print(){

        Iterator<Item> iter = iterator();
        while (iter.hasNext()){
            System.out.print(iter.next() + " ");
        }
        System.out.println();
    }
    */

    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 6; i++){
            rq.enqueue(i);
            //rq.print();
        }
        for (int i = 0; i < 6; i++){
            rq.dequeue();
            //rq.print();
        }
        /*
        0
        0 1
        0 1 2
        2 1 0 3
        3 4 1 2 0
        3 1 5 2 0 4
        2 5 4 3 0
        3 2 0 5
        2 5 0
        0 5
        0
         */
    }
}
