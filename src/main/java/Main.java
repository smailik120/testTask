import Database.Database;
import Database.PostgreSql;
import Operations.SearchOperation;
import Operations.StatOperation;
import Output.ErrorOutput;
import Reader.JsonReader;
import Writer.JsonWriter;

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
       String output = "C:\\outputs\\output.json";
        JsonWriter writer = new JsonWriter();
        if (type.equals("search")) {
            SearchOperation searchOperation = new SearchOperation();
            try {
                String inputData = new JsonReader().read(input);
                String result = searchOperation.action(inputData);
                writer.write(output, result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("stat")) {
            StatOperation statOperation = new StatOperation();
            try {
                String inputData = new JsonReader().read(input);
                String result = statOperation.action(inputData);
                writer.write(output, result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            ErrorOutput errorOutput = new ErrorOutput("operation with this type does not exist");
            try {
                writer.write(output, errorOutput.message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
