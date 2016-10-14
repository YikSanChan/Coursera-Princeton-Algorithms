package PA3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CYS on 2016/10/13.
 */
public class FastCollinearPoints {
    private ArrayList<LineSegment> collinear = new ArrayList<>();
    private ArrayList<innerLineSegment> innerCollinear = new ArrayList<>();
    // 使用内部类表示线段
    private class innerLineSegment implements Comparable<innerLineSegment>{
        public Point start, end;
        public double slope;
        public int nodeNum;

        public innerLineSegment(Point start, Point end, double slope, int nodeNum){
            this.start = start;
            this.end  = end;
            this.slope = slope;
            this.nodeNum = nodeNum;
        }

        public LineSegment toLineSegment(){
            return new LineSegment(start, end);
        }

        public boolean isCollinear(innerLineSegment that){
            return end.slopeTo(that.end) == Double.NEGATIVE_INFINITY && slope == that.slope;
        }

        @Override
        public String toString(){
            return String.format("%s -> %s, slope: %.2f, # node: %d", start, end, slope, nodeNum);
        }
        @Override
        public int compareTo(innerLineSegment that) {
            if (end.compareTo(that.end) < 0) return -1;
            else if (end.compareTo(that.end) > 0) return 1;
            else {
                if (slope < that.slope) return -1;
                else if (slope > that.slope) return 1;
                else {
                    if (nodeNum > that.nodeNum) return -1;
                    else if (nodeNum < that.nodeNum) return 1;
                    else return 0;
                }
            }
        }
    }

    public FastCollinearPoints(Point[] points) {// finds all line segments containing 4 or more points
        // corner case
        if (points == null) throw new NullPointerException("argument to constructor is null.");
        int N = points.length;
        if (N < 4) return;
        for (int i = 0; i < N; i++) {
            if (points[i] == null)
                throw new NullPointerException("exists null Point.");
        }
        // 最长的subLineSegment最早被找到
        // 其后发现的subLineSegment才是真正的sub，且终点和最早找到的一样
        // 因为sort使得最早找到的“最低”
        Arrays.sort(points);
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
                        innerCollinear.add(new innerLineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1], slope, count));
//                        collinear.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
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
                        innerCollinear.add(new innerLineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1], slope, count));
//                        collinear.add(new LineSegment(collinearPoints[0], collinearPoints[collinearPoints.length - 1]));
                    }
                }
            }
        }
    }
    public int numberOfSegments() {// the number of line segments
        return collinear.size();
    }
    public LineSegment[] segments() {// the line segments
//        LineSegment[] ls = new LineSegment[collinear.size()];
//        ls = collinear.toArray(ls);
//        return ls;
        if (innerCollinear.size() == 0) return new LineSegment[0];
        innerLineSegment[] innerLS = new innerLineSegment[innerCollinear.size()];
        innerLS = innerCollinear.toArray(innerLS);
        Arrays.sort(innerLS);
//        for (innerLineSegment ils: innerLS)
//            System.out.println(ils);
//        System.out.println("***********************************");
        collinear.add(innerLS[0].toLineSegment());
        int left = 0, right = 1;
        while (right < innerLS.length){
            if (!innerLS[left].isCollinear(innerLS[right])){
                collinear.add(innerLS[right].toLineSegment());
                left = right;
                right += 1;
            } else {
                right++;
            }
        }
//        for (innerLineSegment ils: innerCollinear)
//            System.out.println(ils);
//        System.out.println("***********************************");
        LineSegment[] ls = new LineSegment[collinear.size()];
        ls = collinear.toArray(ls);
        return ls;

    }

}
