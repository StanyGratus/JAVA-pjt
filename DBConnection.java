
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "DB_CONNECTIVITY_URL";

    private static final String USER = "USERNAME";

    // Replace this with your MySQL root password
    private static final String PASSWORD = "PASSWORD";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
