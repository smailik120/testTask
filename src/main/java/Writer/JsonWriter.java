package Writer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter implements IWriter<String, String> {
    public void write(String fileName, String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(data);
        writer.close();
    }
}
