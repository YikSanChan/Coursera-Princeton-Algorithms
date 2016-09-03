package PA2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by CYS on 2016/9/3.
 */
public class Deque<Item> implements Iterable<Item> {

    static final int INIT_CAPACITY = 2;
    static final int EXPAND_FACTOR = 2;
    static final double SHRINK_FACTOR = 0.25;

    private int first, last; // keep track of first and last position to add in (not for remove)
    private Item[] items; // restore elements of deque
    private int size, capacity;

    /** construct an empty deque */
    public Deque(){
        items = (Item[]) new Object[INIT_CAPACITY];
        first = 0;
        last  = 1;
        size  = 0;
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

    private int getPrev(int index){
        return (index == 0) ? (capacity - 1) : (index - 1);
    }

    private int getNext(int index){
        return (index == capacity - 1) ? 0 : (index + 1);
    }

    private void resize(int newCapacity){
        if (newCapacity < INIT_CAPACITY) return;
        //System.out.println("RESIZE: CURRENT CAPACITY = " + capacity);
        Item[] tmp = (Item[]) new Object[newCapacity];
        first = getNext(first);
        int newLast = 1; // new first set to 0
        for (int i = 0; i < size; i++){
            tmp[newLast++] = items[first];
            first = getNext(first);
        }
        items = tmp;
        first = 0;
        last = newLast;
        capacity = newCapacity;
    }

    public void addFirst(Item item){
        if (item == null) throw new NoSuchElementException();
        if (isFull()) resize(capacity * EXPAND_FACTOR);
        items[first] = item;
        first = getPrev(first);
        size++;
    }

    public void addLast(Item item){
        if (item == null) throw new NoSuchElementException();
        if (isFull()) resize(capacity * EXPAND_FACTOR);
        items[last] = item;
        last = getNext(last);
        size++;
    }

    public Item removeFirst(){
        if (isEmpty()) throw new UnsupportedOperationException();
        first = getNext(first);
        Item toRemove = items[first];
        items[first] = null;
        size--;
        if (isSpacious()) resize((int)(capacity * SHRINK_FACTOR));
        return toRemove;
    }

    public Item removeLast(){
        if (isEmpty()) throw new UnsupportedOperationException();
        last = getPrev(last);
        Item toRemove = items[last];
        items[last] = null;
        size--;
        if (isSpacious()) resize((int)(capacity * SHRINK_FACTOR));
        return toRemove;
    }

    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>{

        private int current;
        private int cnt; // counter
        public DequeIterator(){
            current = getNext(first);
        }
        public boolean hasNext(){
            return cnt < size;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
        public Item next(){
            if (!hasNext()) throw new NoSuchElementException();
            Item currItem = items[current];
            current = getNext(current);
            cnt++;
            return currItem;
        }
    }

    public void print(){
        /* // Do not output as expected
        for (Item i : items){
            System.out.print(i + " ");
        }
        */
        Iterator<Item> iter = iterator();
        while (iter.hasNext()){
            System.out.print(iter.next() + " ");
        }
        System.out.println();

    }

    public static void main(String[] args){
        Deque<Integer> dq = new Deque<>();
        for (int i = 1; i < 12; i++){
            dq.addLast(i);
            dq.print();
        }
        for (int i = 0; i < 11; i++){
            dq.removeFirst();
            dq.print();
        }
    }
}
