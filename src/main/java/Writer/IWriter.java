package Writer;

import java.io.IOException;

public interface IWriter<T,V> {
    public void write(T input, V data) throws IOException;
}
