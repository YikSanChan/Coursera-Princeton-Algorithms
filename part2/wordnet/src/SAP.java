import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Shortest ancestral path
 */
public class SAP {

    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = new Digraph(G); // deep copy
    }

    private int[] shortestPath(Iterable<Integer> vs, Iterable<Integer> ws) {
        validate(vs);
        validate(ws);
        Map<Integer, Integer> distances = new HashMap<>();
        Queue<Integer> q = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        for (int v: vs) {
            q.offer(v);
            visited.add(v);
        }
        int distance = 0;
        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int parent = q.poll();
                distances.put(parent, distance);
                for (int son: G.adj(parent)) {
                    if (visited.add(son)) {
                        q.offer(son);
                    }
                }
            }
            distance++;
        }
        int minDistance = Integer.MAX_VALUE;
        int ancestor = -1;
        q.clear();
        visited.clear();
        for (int w: ws) {
            q.offer(w);
            visited.add(w);
        }
        distance = 0;
        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int parent = q.poll();
                if (distances.containsKey(parent)) {
                    int totalDistance = distance + distances.get(parent);
                    if (totalDistance < minDistance) {
                        minDistance = totalDistance;
                        ancestor = parent;
                    }
                }
                for (int son: G.adj(parent)) {
                    if (visited.add(son)) {
                        q.offer(son);
                    }
                }
            }
            distance++;
        }
        return new int[]{ancestor, minDistance};
    }

    private int[] shortestPath(int v, int w) {
        Iterable<Integer> vs = Collections.singletonList(v);
        Iterable<Integer> ws = Collections.singletonList(w);
        return shortestPath(vs, ws);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int distance = shortestPath(v, w)[1];
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        return distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return shortestPath(v, w)[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        int distance = shortestPath(vs, ws)[1];
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        return distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws) {
        return shortestPath(vs, ws)[0];
    }

    private void validate(Iterable<Integer> vs) {
        if (vs == null) {
            throw new IllegalArgumentException("Invalid input vertices.");
        }
        for (int v: vs) {
            if (v < 0 || v >= G.V()) {
                throw new IllegalArgumentException("Invalid input vertices.");
            }
        }
    }

    // Program arguments: digraph1.txt
    // 3 11 -> length = 4, ancestor = 1
    // 9 12 -> length = 3, ancestor = 5
    // 7 2  -> length = 4, ancestor = 0
    // 1 6  -> length = -1, ancestor = -1
    public static void main(String[] args) {
        In in = new In("test/" + args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}