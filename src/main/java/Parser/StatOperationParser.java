package Parser;

import Exceptions.ParseException;

import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.Map;

public class StatOperationParser implements IParser{
    public Pair<String, String> parse(String json)  {
        Pair<String, String> dates;
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(json, Map.class);
        String startDate = (String) map.get("startDate");
        String endDate = (String) map.get("endDate");
        dates = new Pair<String, String>(startDate, endDate);
        return dates;
    }
}
