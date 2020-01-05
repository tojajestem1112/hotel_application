import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnectionChecker {
    public static void main(String[] args)
    {
        String jdbcUrl = "jdbc:mysql://localhost:3306/hotel_db?serverTimezone=UTC";
        String user = "root";
        String passwd ="root";
        try{
            System.out.println("Connecting to database "+ jdbcUrl);
        Connection myConn = DriverManager.getConnection(jdbcUrl, user, passwd);
            System.out.println("U are connected");

        }
        catch(Exception e)
        {
            System.out.println("u arent connected" + e.getMessage());
        }
    }
}
