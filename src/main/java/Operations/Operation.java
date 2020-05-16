package Operations;

import java.sql.SQLException;

public interface Operation {
    String action(String json) throws SQLException, Exception;
}
