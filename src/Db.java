import java.sql.*;

public class Db {

    Connection con =null;

    public static Connection dbconnect()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3307/quiz","root","maitreya");
            return conn;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
}
