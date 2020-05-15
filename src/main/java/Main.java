import Database.Database;
import Database.PostgreSql;
import Operations.SearchOperation;
import com.google.gson.Gson;

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
        String input="";
        createConnection();
        SearchOperation op = new SearchOperation();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\stas\\Desktop\\input.json"));
            String temp;
            while(reader.ready()) {
                input = input + reader.readLine();
            }
            System.out.println(input);
            Gson gson = new Gson();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(op.action(input));
    }
}
