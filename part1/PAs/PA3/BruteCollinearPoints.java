package PA3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CYS on 2016/10/13.
 */
public class BruteCollinearPoints {
    private ArrayList<LineSegment> collinear = new ArrayList<>();
    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) throw new NullPointerException("argument to constructor is null.");
        int N = points.length;
        for (int i = 0; i < N - 1; i++) {
            if (points[i] == null)
                throw new NullPointerException("exists null Point.");
            for (int j = i + 1; j < N; j++){
                if (points[j] == null)
                    throw new NullPointerException("exists null Point.");
                else if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("contains repeated Point.");
            }
        }
        // 先找出四个共线的
        // 再用排序找一头一尾两个点
        for (int i = 0; i <= N - 4; i++){
            Point pi = points[i];
            for (int j = i + 1; j <= N - 3; j++){
                Point pj = points[j];
                double slope_ij = pi.slopeTo(pj);
                // 共点
                for (int k = j + 1; k <= N - 2; k++){
                    Point pk = points[k];
                    double slope_jk = pj.slopeTo(pk);
                    // 斜率不同
                    if (slope_ij != slope_jk) continue;
                    for (int l = k + 1; l <= N - 1; l++){
                        Point pl = points[l];
                        double slope_kl = pk.slopeTo(pl);
                        if (slope_jk != slope_kl) continue;
                        Point[] line = new Point[]{pi, pj, pk, pl};
                        Arrays.sort(line);
                        collinear.add(new LineSegment(line[0], line[3]));
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
