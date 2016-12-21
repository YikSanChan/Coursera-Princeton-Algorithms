package PA5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by CYS on 2016/12/21.
 */
public class TestKdTree {

    @Test
    public void testInsert(){
        KdTree kdTree = new KdTree();
        assertEquals(kdTree.size(), 0);
        kdTree.insert(new Point2D(0.7, 0.2));
        assertEquals(kdTree.size(), 1);
        kdTree.insert(new Point2D(0.7, 0.2));
        assertEquals(kdTree.size(), 1);
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        assertEquals(kdTree.size(), 3);
        kdTree.insert(new Point2D(0.4, 0.7));
        assertEquals(kdTree.size(), 4);
    }

    @Test
    public void testContains(){
        KdTree kdTree = new KdTree();
        Point2D p = new Point2D(0.0, 0.0);
        assertEquals(kdTree.contains(p), false);
        kdTree.insert(p);
        assertEquals(kdTree.contains(p), true);
    }

    @Test
    public void testRange(){
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        for (Point2D p: kdTree.range(new RectHV(0,0,1,1)))
            System.out.println(p);
        System.out.println("+++++++++++++++++++++++++++++");
        for (Point2D p: kdTree.range(new RectHV(0,0,0.75,0.35)))
            System.out.println(p);
        System.out.println("+++++++++++++++++++++++++++++");
    }

}
