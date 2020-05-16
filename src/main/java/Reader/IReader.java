package Reader;

import java.io.IOException;

public interface IReader<T, V> {
    public T read(V fileName) throws IOException;
}
