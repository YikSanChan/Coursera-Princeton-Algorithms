package PA5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYS on 2016/12/20.
 */
public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect)
        {
            if (rect == null)
                rect = new RectHV(0,0,1,1);
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() {}                            // construct an empty set of points

    public boolean isEmpty()                      // is the set empty?
    {
        return root == null;
    }
    public int size()                         // number of points in the set
    {
        return size;
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new NullPointerException("Insert null.");
        root = insert(root, p, new RectHV(0, 0, 1, 1), true); // vertical
    }

    /**
     * Insert a point into KdTree
     * @param x current node
     * @param p point to insert
     * @param rect rectangle of current node
     * @param orientation orientation of current node
     * @return
     */
    private Node insert(Node x, Point2D p, RectHV rect, boolean orientation)
    {
        if (x == null)
        {
            size++;
            return new Node(p, rect);
        }
        if (x.p.equals(p)) return x;
        int cmp = (orientation ? Point2D.X_ORDER : Point2D.Y_ORDER).compare(p, x.p);
        if (cmp < 0)
        {
            //don't have to call RectHV methods until you reached null
            if (orientation) x.lb = insert(x.lb, p, x.lb == null ? new RectHV(rect.xmin(), rect.ymin(), x.p.x(), rect.ymax()) : x.lb.rect, !orientation);
            else             x.lb = insert(x.lb, p, x.lb == null ? new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.p.y()) : x.lb.rect, !orientation);
        }
        else
        {
            if (orientation) x.rt = insert(x.rt, p, x.rt == null ? new RectHV(x.p.x(), rect.ymin(), rect.xmax(), rect.ymax()) : x.rt.rect, !orientation);
            else             x.rt = insert(x.rt, p, x.rt == null ? new RectHV(rect.xmin(), x.p.y(), rect.xmax(), rect.ymax()) : x.rt.rect, !orientation);
        }
        return x;
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new NullPointerException("Check contains null.");
        return contains(root, p, true);
    }

    /**
     * Helper function of public boolean contains(Point2D)
     * @param x Current node
     * @param p Point2D point
     * @param orientation Orientation that current node has: vertical (true) OR horizontal (false)
     * @return Whether subtree of x (including x) contains p
     */
    private boolean contains(Node x, Point2D p, boolean orientation)
    {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        int cmp = (orientation ? Point2D.X_ORDER : Point2D.Y_ORDER).compare(p, x.p);
        if (cmp < 0)
            return contains(x.lb, p, !orientation);
        else
            return contains(x.rt, p, !orientation);
    }
    public void draw()                         // draw all points to standard draw
    {
        Queue<Node> q = new Queue<>();
        List<Boolean> l = new ArrayList<>();
        enqueueAll(root, q, l, true);

        int i = 0; // index of list l
        for (Node n: q)
        {
            // draw points
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(n.p.x(), n.p.y());

            // draw lines
            StdDraw.setPenColor(l.get(i) ? StdDraw.RED : StdDraw.BLUE);
            StdDraw.setPenRadius();
            if (l.get(i))
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            else
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

            // increment index of list storing each node's orientation
            i++;
        }
    }

    /**
     * Enqueue all nodes in the tree into queue q, and also each node's orientation
     * @param x current node
     * @param q queue storing all nodes
     * @param l queue storing all nodes' orientation
     * @param b current node's orientation: vertical (true) OR horizontal (false)
     */
    private void enqueueAll(Node x, Queue<Node> q, List<Boolean> l, boolean b)
    {
        if (x == null) return;
        enqueueAll(x.lb, q, l, !b);
        q.enqueue(x);
        l.add(b);
        enqueueAll(x.rt, q, l, !b);
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        if (rect == null)
            throw new NullPointerException("Rectangle is null.");
        Queue<Point2D> q = new Queue<>();
        range(rect, root, q, true);
        return q;
    }

    /**
     * Enqueue x.p into q if x.p is inside query rectangle rect
     * @param rect query rectangle
     * @param x current node being processed
     * @param q queue storing all points inside query rectangle
     */
    private void range(RectHV rect, Node x, Queue<Point2D> q, boolean orientation)
    {
        if (x == null) return;
        /**
         * Prune #3
         * Instead of checking whether the query rectangle intersects the rectangle corresponding to a node,
         * it suffices to check only whether the query rectangle intersects the splitting line segment:
         * if it does, then recursively search both subtrees;
         * otherwise, recursively search the one subtree where points intersecting the query rectangle could be.
         */
        RectHV splittingLine = null;
        if (orientation) splittingLine = new RectHV(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        else             splittingLine = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        if (rect.intersects(splittingLine))
        {
            range(rect, x.lb, q, !orientation);
            if (rect.contains(x.p)) q.enqueue(x.p);
            range(rect, x.rt, q, !orientation);
        }
        else
        {
            if (orientation) range(rect, (x.p.x() > rect.xmax()) ? x.lb : x.rt, q, !orientation);
            else             range(rect, (x.p.y() > rect.ymax()) ? x.lb : x.rt, q, !orientation);
        }
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new NullPointerException("Given point to find nearest is null.");
        if (isEmpty()) return null;
        return nearest(p, root, root.p, true);
    }

    /**
     * Nearest point to query point p inside the subtree rooted at x.
     * Need to prune a few times
     * @param p query point
     * @param x current node
     * @param best current closest point to query point p
     * @param orientation current node's orientation
     * @return nearest point to query point p inside the subtree rooted at x
     */
    private Point2D nearest(Point2D p, Node x, Point2D best, boolean orientation)
    {
        if (x == null) return best;
        // prune
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(best))
            best = x.p;
        /*
         Always choose the subtree that is on the same side of the splitting line as the query point
         as the first subtree to explore—the closest point found while exploring the first subtree
         may enable pruning of the second subtree.
         */
        if (x.rect.distanceSquaredTo(p) < p.distanceSquaredTo(best))
        {
            if ((orientation && p.x() < x.p.x()) || (!orientation && p.y() < x.p.y()))
            {
                best = nearest(p, x.lb, best, !orientation);
                best = nearest(p, x.rt, best, !orientation);
            }
            else
            {
                best = nearest(p, x.rt, best, !orientation);
                best = nearest(p, x.lb, best, !orientation);
            }
        }
        return best;
    }
}
