package Operations;

import Criteria.Criteria;
import Criteria.Family;
import Criteria.ProductName;
import Criteria.MinMax;
import Criteria.PassiveBuyers;
import Database.PostgreSql;
import Exceptions.ParseException;
import Other.Pair;
import Output.ErrorOutput;
import Output.SearchOutput;
import Parser.InputParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.binding.ObjectBinding;
import models.Buyer;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SearchOperation implements Operation {
    private PostgreSql db = PostgreSql.getDatabase();

    void isDouble(String input) {
        int temp = (int) (Double.parseDouble(input) + Double.MIN_VALUE);
        if ((temp + Double.MIN_VALUE) < (Double.parseDouble(input))) {
            throw new NumberFormatException();
        }
    }

    public List<Pair<Criteria, List<Buyer>>> getOutputList(List<List<String>> pair) throws ParseException {
        List<Pair<Criteria, List<Buyer>>> output = new ArrayList<Pair<Criteria, List<Buyer>>>();
        for (int i = 0; i < pair.size(); i++) {
            if (pair.get(i).get(0).equals("lastName") && pair.get(i).size() == 2) {
                String lastName = pair.get(i).get(1);
                output.add(new Pair(new Family(lastName), getDataFromFamilyCriteria(lastName)));
            } else if (pair.get(i).get(0).equals("productName") && pair.get(i + 1).get(0).equals("minTimes") && i + 1 < pair.size()) {
                String productName = pair.get(i).get(1);
                String minTimes = pair.get(i + 1).get(1);
                i = i + 1;
                isDouble(minTimes);
                output.add(new Pair(new ProductName(productName, (int) (Double.parseDouble(minTimes))), getDataFromProductNameCriteria(productName, minTimes)));
            } else if (pair.get(i).get(0).equals("minExpenses") && pair.get(i + 1).get(0).equals("maxExpenses") && i + 1 < pair.size()) {
                String minExpenses = pair.get(i).get(1);
                String maxExpenses = pair.get(i + 1).get(1);
                i = i + 1;
                isDouble(minExpenses);
                isDouble(maxExpenses);
                output.add(new Pair(new MinMax((int) (Double.parseDouble(minExpenses)), (int) (Double.parseDouble(maxExpenses))), getDataFromMinMaxCriteria(minExpenses, maxExpenses)));
            } else if (pair.get(i).get(0).equals("badCustomers") && pair.get(i).size() == 2) {
                String badCustomers = pair.get(i).get(1);
                isDouble(badCustomers);
                output.add(new Pair(new PassiveBuyers((int)Double.parseDouble(badCustomers)), getDataFromPassiveBuyersCriteria(badCustomers)));
            } else {
                throw new ParseException("Incorrect input data");
            }
        }
        return output;
    }

    public String action(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String result = "";
        List<List<String>> pair = new InputParser().parse(json);
        try {
            List<Pair<Criteria, List<Buyer>>> output = getOutputList(pair);
            SearchOutput out = new SearchOutput(output);
            result = gson.toJson(out);
        } catch (ParseException e) {
            result = gson.toJson(new ErrorOutput(e.getMessage()));
        } catch (NumberFormatException e) {
            result = gson.toJson(new ErrorOutput("field minTimes and minExpress and maxExpress must be integer"));
        }
        return result;
    }

    public List<Buyer> getDataFromFamilyCriteria(String lastName) {
        List<Buyer> buyers = new ArrayList<Buyer>();
        try {
            Statement s = db.getConnection().createStatement();
            String query = "SELECT * FROM public.\"Buyers\" where name='" + lastName + "'";
            ResultSet resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                String buyerName = resultSet.getString("name");
                String buyerLastName = resultSet.getString("lastName");
                buyers.add(new Buyer(buyerName, buyerLastName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buyers;
    }

    public List<Buyer> getDataFromProductNameCriteria(String productName, String minTimes) {
        List<Buyer> buyers = new ArrayList<Buyer>();
        try {
            Statement s = db.getConnection().createStatement();
            String query = "SELECT buyer_id, product_name, public.\"Buyers\".\"name\", public.\"Buyers\".\"lastName\" FROM public.\"Purchases\" inner join public.\"Buyers\" on buyer_id = id GROUP BY buyer_id,product_name, public.\"Buyers\".\"name\", public.\"Buyers\".\"lastName\" having COUNT(product_name) >= " + minTimes + " and product_name='" + productName + "'";
            ResultSet resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                buyers.add(new Buyer(name, lastName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buyers;
    }

    public List<Buyer> getDataFromMinMaxCriteria(String minExpenses, String maxExpenses) {
        List<Buyer> buyers = new ArrayList<Buyer>();
        try {
            Statement s = db.getConnection().createStatement();
            String query = "CREATE TEMP TABLE t AS SELECT * FROM (SELECT buyer_id, product_name, (COUNT(product_name)* p.price) as mul FROM public.\"Purchases\"  inner join public.\"Products\" as p on product_name = name GROUP BY buyer_id,product_name,p.price) as priceForEveryProduct;";
            s.execute(query);
            query = "CREATE TEMP TABLE t1 AS SELECT * FROM (select buyer_id, SUM(mul) from t group by buyer_id having SUM(mul) >= " + minExpenses + " and SUM(mul)<=" + maxExpenses + ") as PriceForEveryBuyer;";
            s.execute(query);
            query = "select name, public.\"Buyers\".\"lastName\" from public.\"Buyers\" inner join t1 on buyer_id = id;";
            ResultSet resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                buyers.add(new Buyer(name, lastName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buyers;
    }

    public List<Buyer> getDataFromPassiveBuyersCriteria(String badCustomers) {
        List<Buyer> buyers = new ArrayList<Buyer>();
        try {
            Statement s = db.getConnection().createStatement();
            String query = "SELECT buyer_id, Count(buyer_id), public.\"Buyers\".\"name\" as n, public.\"Buyers\".\"lastName\" as l FROM public.\"Purchases\" inner join public.\"Buyers\" on buyer_id = id GROUP BY buyer_id, n, l order by count(buyer_id);";
            s.execute(query);
            int counter = 0;
            ResultSet resultSet = s.executeQuery(query);
            while (resultSet.next() && counter < Double.parseDouble(badCustomers)) {
                counter++;
                String name = resultSet.getString("n");
                String lastName = resultSet.getString("l");
                buyers.add(new Buyer(name, lastName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buyers;
    }

}
