import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordnet;

    public Outcast(WordNet wordnet) { // constructor takes a WordNet object
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) { // given an array of WordNet nouns, return an outcast
        if (nouns == null || nouns.length < 2) {
            throw new IllegalArgumentException("outcast() can take only String array with length >= 2.");
        }
        int len = nouns.length;
        String res = null;
        int maxDistance = -1;
        for (int i = 0; i < len; i++) {
            int distance = 0;
            for (int j = 0; j < len; j++) {
                if (j == i) {
                    continue;
                }
                distance += wordnet.distance(nouns[i], nouns[j]);
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                res = nouns[i];
            }
        }
        return res;
    }

    // Program arguments: synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("test/" + args[0], "test/" + args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In("test/" + args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}