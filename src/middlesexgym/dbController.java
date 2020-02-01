package middlesexgym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author LaveshPanjwani
 */
public class dbController {

    private String connectionString = "jdbc:mysql://127.0.0.1:3306/GYM";
    private String username = "root";
    private String password = "example";
    private Connection dbConnection;

    public dbController() {
        try {
            dbConnection = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(dbController.class.getName()).log(Level.SEVERE, null, ex);
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
