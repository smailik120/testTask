package Reader;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader implements IReader {
    public String read(String fileName) throws IOException {
        String input = "";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String temp;
        while (reader.ready()) {
            input = input + reader.readLine();
        }
        return input;
    }
}
