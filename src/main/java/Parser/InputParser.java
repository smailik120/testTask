package Parser;

import Criteria.Criteria;
import Other.Pair;
import com.google.gson.Gson;
import models.Buyer;

import java.util.*;

public class InputParser implements IParser {
    public List<List<String>> parse(String json) throws NullPointerException {
        Gson gson = new Gson();
        List<List<String>> parseResult = new ArrayList<List<String>>();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        Collection<Object> criterias = (Collection<Object>) map.get("criterias");
        for (Object criteria : criterias) {
            String criteriaToString = criteria.toString();
            String[] res = criteriaToString.split(",");
            for (int i = 0; i < res.length; i++) {
                String result = res[i].replace("{", "").replace("}", "");
                if (result.charAt(0) == ' ') {
                    result = result.substring(1);
                }
                parseResult.add(Arrays.asList(result.split("=")));
                if (parseResult.get(i).get(0).charAt(0) == ' ') {
                    parseResult.get(i).get(0).substring(1);
                }
            }
        }
        return parseResult;
    }
}
