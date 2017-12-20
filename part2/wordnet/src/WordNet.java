import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private final Map<Integer, Synset> synsets;
    private final Map<String, List<Integer>> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    // time: linear
    public WordNet(String synsetsFile, String hypernymsFile) {
        if (synsetsFile == null || hypernymsFile == null) {
            throw new IllegalArgumentException("Arguments to WordNet() should not be null.");
        }
        synsets = new HashMap<>();
        nouns = new HashMap<>();
        In in = new In(synsetsFile);
        String line;
        while (in.hasNextLine()) {
            line = in.readLine();
            String[] split = line.trim().split(",");
            int id = Integer.parseInt(split[0]);
            List<String> synonyms = new ArrayList<>();
            synonyms.addAll(Arrays.asList(split[1].split("\\s+")));
            synsets.put(id, new Synset(synonyms));
            for (String word: synonyms) {
                if (!nouns.containsKey(word)) {
                    nouns.put(word, new ArrayList<>());
                }
                nouns.get(word).add(id);
            }
        }
        in.close();
        Digraph G = new Digraph(synsets.size());
        in = new In(hypernymsFile);
        while (in.hasNextLine()) {
            line = in.readLine();
            String[] split = line.trim().split(",");
            int from = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                G.addEdge(from, Integer.parseInt(split[i]));
            }
        }
        in.close();
        // check whether G is DAG
        if (!hasOneRoot(G) || !isDAG(G)) {
            throw new IllegalArgumentException("Not a rooted DAG.");
        }

        sap = new SAP(G);
    }

    private boolean hasOneRoot(Digraph G) {
        int rootCount = 0;
        for (int v: synsets.keySet()) {
            if (G.indegree(v) > 0 && G.outdegree(v) == 0) {
                rootCount++;
            }
        }
        return rootCount == 1;
    }

    private boolean isDAG(Digraph G) {
        DirectedCycle cycleDetector = new DirectedCycle(G);
        return !cycleDetector.hasCycle();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    // time: log
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Arguments to isNoun() should not be null.");
        }
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    // time: linear
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Distance can only be measured when both nouns are in wordnet.");
        }
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and noun
    // in a shortest ancestral path (defined below)
    // time: linear
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Ancestor can only be returned when both nouns are in wordnet.");
        }
        int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        return String.join(" ", synsets.get(ancestor).getSynonyms());
    }

    public static void main(String[] args) {
//        WordNet wordNet = new WordNet("test/synsets.txt", "test/hypernyms.txt");
    }
}