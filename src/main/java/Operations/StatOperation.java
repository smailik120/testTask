package Operations;
import Database.PostgreSql;
import Exceptions.NegativeDateException;
import Other.DateWrapper;
import Output.ErrorOutput;
import Output.Output;
import Output.StatOutput;
import Parser.StatOperationParser;
import com.google.gson.Gson;
import javafx.util.Pair;
import models.Buyer;
import models.Customer;
import models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatOperation implements Operation {
    private PostgreSql db = PostgreSql.getDatabase();

    public String action(String json) {
        Gson gson = new Gson();
        Pair<String, String> pair = new StatOperationParser().parse(json);
        String result = gson.toJson(getStat(pair.getKey(), pair.getValue()));
        return result;
    }

    private Output getStat(String startDate, String endDate) {
        StatOutput output = null;
        int sum;
        int totalExpenses = 0;
        int counter = 0;
        int days;
        List<Customer> customers = new ArrayList<Customer>();
        Map<Pair<Integer, String>, List<Product>> result = new HashMap<Pair<Integer, String>, List<Product>>();
        Statement s;
        try {
            s = db.getConnection().createStatement();
            String query = "create temp table t3 as select * from (select buyer_id, product_name, Count(product_name), public.\"Products\".\"price\" as price, price*Count(product_name) as sum  from public.\"Purchases\" inner join public.\"Products\" on product_name = name where date >= '" + startDate +"' and date <= '" + endDate + "' group by buyer_id, product_name, price order by sum desc) as b;";
            s.execute(query);
            query = "select sum, public.\"Buyers\".\"name\", public.\"Buyers\".\"lastName\", product_name, buyer_id from t3 inner join public.\"Buyers\" on buyer_id = id;";
            ResultSet resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name") + " " + resultSet.getString("lastName");
                int buyer_id = resultSet.getInt("buyer_id");
                String product_name = resultSet.getString("product_name");
                int expenses = resultSet.getInt("sum");
                Product p = new Product(product_name, expenses);
                Pair<Integer, String> key = new Pair<Integer, String>(buyer_id, name);
                if (result.get(key) == null) {
                    List<Product> products = new ArrayList<Product>();
                    products.add(p);
                    result.put(key, products);
                }
                else {
                    result.get(key).add(p);
                }
            }
            for(Pair<Integer, String> pair: result.keySet()) {
                sum = 0;
                for (Product products : result.get(pair)) {
                    sum = sum + products.getExpenses();
                }
                counter++;
                totalExpenses = totalExpenses + sum;
                Customer customer = new Customer(pair.getValue(), result.get(pair), sum);
                customers.add(customer);
            }

            try {
                DateWrapper dateWrapper = new DateWrapper();
                days = dateWrapper.getDaysBetweenDates(startDate, endDate);
            } catch (NegativeDateException e) {
                return new ErrorOutput(e.getMessage());
            }
            try {
                System.out.println(days);
                output = new StatOutput((int) days, customers, totalExpenses, totalExpenses / counter);
            }
            catch (ArithmeticException e) {
                output = new StatOutput((int) days, customers, totalExpenses, 0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }
}
