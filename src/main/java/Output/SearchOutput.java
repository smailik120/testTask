package Output;

import Criteria.Criteria;
import Other.Pair;
import models.Buyer;

import java.util.List;

public class SearchOutput {
    public final String type= "search";
    public List<Pair<Criteria, List<Buyer>>> results;

    public SearchOutput(List<Pair<Criteria, List<Buyer>>> results) {
        this.results = results;
    }
}
