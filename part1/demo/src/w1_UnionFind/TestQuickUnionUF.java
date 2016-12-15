package w1_UnionFind;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by CYS on 2016/12/14.
 */
public class TestQuickUnionUF {

    QuickUnionUF uf;

    /**
     * Test connected and count
     */
    @Test
    public void testConnected(){
        uf = new QuickUnionUF(10);
        assertEquals(uf.count(), 10);
        uf.union(1,2);uf.union(7,2);
        assertEquals(uf.count(), 8);
        assertEquals(uf.connected(1,7), true);
        uf.union(2,6);uf.union(0,6);uf.union(5,6);
        assertEquals(uf.count(), 5);
        assertEquals(uf.connected(1,6), true);
        assertEquals(uf.connected(1,3), false);
        uf.union(3,4);uf.union(8,4);uf.union(9,4);
        assertEquals(uf.count(), 2);
        assertEquals(uf.connected(3,9), true);
        uf.union(4,1);
        assertEquals(uf.count(), 1);
        assertEquals(uf.connected(8,7), true);
    }
}
