import Database.Database;
import Database.PostgreSql;
import Operations.SearchOperation;
import Operations.StatOperation;
import Reader.JsonReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void createConnection() {
        PostgreSql.setAddress("localhost");
        PostgreSql.setDbName("Shop");
        PostgreSql.setPassword("1234");
        PostgreSql.setUserName("postgres");
        PostgreSql.setPort("5432");
    }
    public static void main(String[] args) {
        createConnection();
       String type = "search";
       String input = "C:\\inputs\\input.json";
       //String output = args[2];
        if (type.equals("search")) {
            SearchOperation searchOperation = new SearchOperation();
            try {
                String inputData = new JsonReader().read(input);
                searchOperation.action(inputData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("stat")) {
            StatOperation statOperation = new StatOperation();
            try {
                String inputData = new JsonReader().read(input);
                statOperation.action(inputData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

        }

    }
}
