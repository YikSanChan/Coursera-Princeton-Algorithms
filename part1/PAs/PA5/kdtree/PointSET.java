import edu.princeton.cs.algs4.*;

/**
 * Created by CYS on 2016/12/20.
 */
public class PointSET {

    private SET<Point2D> set;

    public PointSET()                               // construct an empty set of points
    {
        set = new SET<>();
    }
    public boolean isEmpty()                      // is the set empty?
    {
        return set.isEmpty();
    }
    public int size()                         // number of points in the set
    {
        return set.size();
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new NullPointerException("Insert null.");
        set.add(p);
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new NullPointerException("Check contains null.");
        return set.contains(p);
    }
    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p: set)
            StdDraw.point(p.x(), p.y());
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        if (rect == null)
            throw new NullPointerException("Rectangle is null.");
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p: set)
            if (rect.contains(p))
                queue.enqueue(p);
        return queue;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new NullPointerException("Given point to find nearest is null.");
        if (isEmpty()) return null;
        double minDistSquare = Double.MAX_VALUE, tmp;
        Point2D nearestPoint = null;
        for (Point2D other: set)
            if ((tmp = p.distanceSquaredTo(other)) < minDistSquare)
            {
                minDistSquare = tmp;
                nearestPoint = other;
            }
        return nearestPoint;
    }
    public static void main(String[] args){
        PointSET pointSET = new PointSET();
        //Simple test
        double[][] xy = new double[11][2];
        for (int i = 0; i < xy.length; i++)
            xy[i][0] = xy[i][1] = i * 0.1;
        for (double[] point: xy)
            pointSET.insert(new Point2D(point[0], point[1]));
        pointSET.draw();
    }
}