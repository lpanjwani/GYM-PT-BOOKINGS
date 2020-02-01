/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author LaveshPanjwani
 */
public class dbActions {

    private final dbController db = new dbController();

    private ArrayList<String> bookingsDecode(ResultSet dbRes) throws SQLException {
        ArrayList<String> bookings = new ArrayList<>();

        while (dbRes.next()) {
            String current = ("ID: " + dbRes.getInt("id"));
            current += " Client: " + dbRes.getString("client");
            current += " Trainer: " + dbRes.getString("trainer");
            current += " Date: " + dbRes.getDate("date");
            current += " Start Time: " + dbRes.getTime("startTime");
            current += " End Time: " + dbRes.getTime("endTime");
            current += " Focus: " + dbRes.getString("focus");
            bookings.add(current);
        }

        return bookings;

    }

    public ArrayList getBookings() throws SQLException {
        ResultSet dbRes = db.runQuery(
                "SELECT\n" + "    bookings.id,\n" + "    CONCAT(client.name, ' (ID ', client.id, ')') AS client,\n"
                + "    CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer,\n" + "    bookings.date,\n"
                + "    bookings.startTime,\n" + "    bookings.endTime,\n"
                + "    CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus\n" + "FROM\n" + "    GYM.bookings\n"
                + "    INNER JOIN client ON client.id = bookings.client\n"
                + "    INNER JOIN staff ON staff.id = bookings.trainer\n"
                + "    INNER JOIN focus ON focus.id = bookings.focus;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByID(int id) throws SQLException {
        System.out.println("SELECT\n" + "    bookings.id,\n" + "    CONCAT(client.name, ' (ID ', client.id, ')') AS client,\n"
                + "    CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer,\n" + "    bookings.date,\n"
                + "    bookings.startTime,\n" + "    bookings.endTime,\n"
                + "    CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus\n" + "FROM\n" + "    GYM.bookings\n"
                + "    INNER JOIN client ON client.id = bookings.client\n"
                + "    INNER JOIN staff ON staff.id = bookings.trainer\n"
                + "    INNER JOIN focus ON focus.id = bookings.focus\n" + "WHERE\n" + "    bookings.id = " + id + ";");
        ResultSet dbRes = db.runQuery(
                "SELECT\n" + "    bookings.id,\n" + "    CONCAT(client.name, ' (ID ', client.id, ')') AS client,\n"
                + "    CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer,\n" + "    bookings.date,\n"
                + "    bookings.startTime,\n" + "    bookings.endTime,\n"
                + "    CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus\n" + "FROM\n" + "    GYM.bookings\n"
                + "    INNER JOIN client ON client.id = bookings.client\n"
                + "    INNER JOIN staff ON staff.id = bookings.trainer\n"
                + "    INNER JOIN focus ON focus.id = bookings.focus\n" + "WHERE\n" + "    bookings.id = " + id + ";");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByClient(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT\n" + "    bookings.id,\n"
                + "    CONCAT(client.name, ' (ID ', client.id, ')') AS client,\n"
                + "    CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer,\n" + "    bookings.date,\n"
                + "    bookings.startTime,\n" + "    bookings.endTime,\n"
                + "    CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus\n" + "FROM\n" + "    GYM.bookings\n"
                + "    INNER JOIN client ON client.id = bookings.client\n"
                + "    INNER JOIN staff ON staff.id = bookings.trainer\n"
                + "    INNER JOIN focus ON focus.id = bookings.focus\n" + "WHERE\n" + "    client.id = " + id + ";");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByPT(int id) throws SQLException {

        ResultSet dbRes = db.runQuery("SELECT\n" + "    bookings.id,\n"
                + "    CONCAT(client.name, ' (ID ', client.id, ')') AS client,\n"
                + "    CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer,\n" + "    bookings.date,\n"
                + "    bookings.startTime,\n" + "    bookings.endTime,\n"
                + "    CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus\n" + "FROM\n" + "    GYM.bookings\n"
                + "    INNER JOIN client ON client.id = bookings.client\n"
                + "    INNER JOIN staff ON staff.id = bookings.trainer\n"
                + "    INNER JOIN focus ON focus.id = bookings.focus\n" + "WHERE\n" + "    staff.id = " + id + ";");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByDate(Date date) throws SQLException {
        ResultSet dbRes = db.runQuery(
                "SELECT\n" + "    bookings.id,\n" + "    CONCAT(client.name, ' (ID ', client.id, ')') AS client,\n"
                + "    CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer,\n" + "    bookings.date,\n"
                + "    bookings.startTime,\n" + "    bookings.endTime,\n"
                + "    CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus\n" + "FROM\n" + "    GYM.bookings\n"
                + "    INNER JOIN client ON client.id = bookings.client\n"
                + "    INNER JOIN staff ON staff.id = bookings.trainer\n"
                + "    INNER JOIN focus ON focus.id = bookings.focus\n" + "WHERE\n"
                + "    DATE(bookings.date) = '" + date + "';");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

}
