/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class deals with Database Connections & Native Java Management
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public class Database {

    private String connectionString = "jdbc:mysql://127.0.0.1:3306/GYM";
    // private String connectionString = "jdbc:mysql://160.153.129.20:3306/GYM";
    private String username = "root";
    private String password = "";
    private Connection dbConnection;

    public Database() {
        try {
            dbConnection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet runQuery(String sqlQuery) {
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery);
            // ... add parameters to the SQL query using PreparedStatement methods:
            // setInt, setString, etc.
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet;
        } catch (SQLException e) {
            // ... handle SQL exception
            // Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public int runUpdate(String sqlQuery) {
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery);
            // ... add parameters to the SQL query using PreparedStatement methods:
            // setInt, setString, etc.
            int result = preparedStatement.executeUpdate();

            return result;
        } catch (SQLException e) {
            // ... handle SQL exception
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
        return 2;
    }

}
