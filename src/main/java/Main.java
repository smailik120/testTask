import Database.Database;
import Database.PostgreSql;
import Operations.SearchOperation;
import Operations.StatOperation;
import Output.ErrorOutput;
import Reader.JsonReader;
import Writer.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        String type = args[0];
        String input = args[1];
        String output = args[2];
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        JsonWriter writer = new JsonWriter();
        try {
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
            if (type.equals("stat")) {
                StatOperation statOperation = new StatOperation();
                try {
                    String inputData = new JsonReader().read(input);
                    String result = statOperation.action(inputData);
                    writer.write(output, result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (args.length != 3) {
                ErrorOutput errorOutput = new ErrorOutput("error input args");
                try {
                    writer.write(output, gson.toJson(errorOutput, ErrorOutput.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!type.equals("stat") && !type.equals("search")) {
                ErrorOutput errorOutput = new ErrorOutput("operation with this type does not exist");
                try {
                    writer.write(output, gson.toJson(errorOutput, ErrorOutput.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            ErrorOutput errorOutput = new ErrorOutput("error");
            try {
                writer.write(output, gson.toJson(errorOutput, ErrorOutput.class));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
