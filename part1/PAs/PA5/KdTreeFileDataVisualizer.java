package PA5;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by CYS on 2016/12/21.
 */

/**
 * Read test data to print points and splitting lines.
 */
public class KdTreeFileDataVisualizer {

    public static void main(String[] args){
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdTree = new KdTree();
        String fileName = "testing\\kdtree\\circle10.txt";
        Scanner input = null;
        try {
            input = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert input != null;
        while (input.hasNextLine()){
            double x = input.nextDouble();
            double y = input.nextDouble();
            Point2D p = new Point2D(x, y);
            if (rect.contains(p)) {
                StdOut.printf("%8.6f %8.6f\n", x, y);
                kdTree.insert(p);
                StdDraw.clear();
                kdTree.draw();
                StdDraw.show();
            }
            StdDraw.pause(50);
        }
    }
}
