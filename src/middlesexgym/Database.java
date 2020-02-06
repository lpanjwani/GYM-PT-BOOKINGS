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
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class deals with Database Connections & Native Java Management
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public class Database {

    private String connectionString = "jdbc:mysql://127.0.0.1:3306/GYM";
    private String username = "root";
    private String password = "";
    private Connection dbConnection;

    // Lock Definition for allowing concurrency
    private final ReentrantLock lock = new ReentrantLock();

    public Database() {
        try {
            dbConnection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet runQuery(String sqlQuery) {
        try {
            // Acquire Lock so other threads cannot access the Database (Concurrency)
            lock.lock();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet;
        } catch (SQLException e) {
            // Handle SQL exception
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            // Remove Lock so other threads can access the Database (Concurrency)
            lock.unlock();
        }
        return null;
    }

    public int runUpdate(String sqlQuery) {
        try {
            // Acquire Lock so other threads cannot access the Database (Concurrency)
            lock.lock();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery);
            int result = preparedStatement.executeUpdate();

            return result;
        } catch (SQLException e) {
            // Handle SQL exception
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            // Remove Lock so other threads can access the Database (Concurrency)
            lock.unlock();
        }
        return 2;
    }

}
