import java.util.List;

public class Synset {

    private List<String> synonyms;

    public Synset(List<String> synonyms) {
        this.synonyms.addAll(synonyms);
    }

    public List<String> getSynonyms() {
        return synonyms;
    }
}
