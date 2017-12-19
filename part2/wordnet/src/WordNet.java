import edu.princeton.cs.algs4.Digraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class WordNet {

    private Map<Integer, Synset> synsets;
    private Digraph G;
    private Set<String> nouns;

    // constructor takes the name of the two input files
    // time: linear
    public WordNet(String synsetsFile, String hypernymsFile) {
        if (synsetsFile == null || hypernymsFile == null) {
            throw new IllegalArgumentException("Arguments to constructor should not be null.");
        }
        synsets = new HashMap<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(synsetsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] split = line.trim().split(",");
            int id = Integer.parseInt(split[0]);
            List<String> synonyms = Arrays.stream(split[1].split("\\s+")).collect(Collectors.toList());
            nouns.addAll(synonyms);
            synsets.put(id, new Synset(synonyms));
        }
        scanner.close();
        G = new Digraph(synsets.size());
        try {
            scanner = new Scanner(new File(hypernymsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] split = line.trim().split(",");
            int from = Integer.parseInt(split[0]);
            for (int i = 1; i < split.length; i++) {
                G.addEdge(from, Integer.parseInt(split[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns;
    }

    // is the word a WordNet noun?
    // time: log
    public boolean isNoun(String word) {
        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    // time: linear
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Distance can only be measured when both input words are in wordnet.");
        }
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    // time: linear
    public String sap(String nounA, String nounB) {
        return "";
    }
    // do unit testing of this class
    public static void main(String[] args) {
    }
}