package PA3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CYS on 2016/10/13.
 */
public class FastCollinearPoints {
    private ArrayList<LineSegment> collinear = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {// finds all line segments containing 4 or more points
        // corner case
        if (points == null) throw new NullPointerException("argument to constructor is null.");
        int N = points.length;
        for (int i = 0; i < N; i++) {
            if (points[i] == null)
                throw new NullPointerException("exists null Point.");
        }
        for (int i = 0; i < N; i++) { // iterate through Point p
            Point p = points[i];
            Point[] qArray = new Point[N - 1];
            // copy the array without ith element
            System.arraycopy(points, 0, qArray, 0, i);
            System.arraycopy(points, i + 1, qArray, i, N - 1 - i);
            Arrays.sort(qArray, p.slopeOrder());
            // iterate through q
            int count = 2; // initial number of collinear Point.
            int begin = 0;
            double slope = p.slopeTo(qArray[0]);
            for (int j = 1; j < qArray.length; j++){
                if (p.slopeTo(qArray[j]) != slope){
                    if (count >= 4){
                        Point[] collinearPoints = new Point[count];
                        collinearPoints[0] = p;
                        System.arraycopy(qArray, begin, collinearPoints, 1, count - 1);
                        Arrays.sort(collinearPoints);
                        collinear.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
                    }
                    // renew
                    begin = j;
                    slope = p.slopeTo(qArray[j]);
                    count = 2;
                }
                else { // p.slopeTo(qArray[j]) == slope
                    count++;
                    if (j == qArray.length - 1 && count >= 4){
                        Point[] collinearPoints = new Point[count];
                        collinearPoints[0] = p;
                        System.arraycopy(qArray, begin, collinearPoints, 1, count - 1);
                        Arrays.sort(collinearPoints);
                        collinear.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
                    }
                }
            }
        }
    }
    public int numberOfSegments() {// the number of line segments
        return collinear.size();
    }
    public LineSegment[] segments() {// the line segments
        LineSegment[] ls = new LineSegment[collinear.size()];
        ls = collinear.toArray(ls);
        return ls;
    }

}
