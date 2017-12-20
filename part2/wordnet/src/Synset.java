import java.util.ArrayList;
import java.util.List;

public class Synset {

    private final List<String> synonyms;

    public Synset(List<String> synonyms) {
        this.synonyms = new ArrayList<>();
        this.synonyms.addAll(synonyms);
    }

    public List<String> getSynonyms() {
        return synonyms;
    }
}
